package com.example.portsandadapters.architecture;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaField;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

/**
 * Automatically verifies 12-Factor App rules that can be checked
 * via static analysis (bytecode).
 *
 *   II.  Dependencies    -> no Runtime.exec / ProcessBuilder
 *   IV.  Backing services -> domain must not import infrastructure
 *   VI.  Processes        -> no mutable static state
 *   XI.  Logs             -> no System.out / FileWriter
 *
 * Factors I, III, V, VIII, X, XII are not verifiable with ArchUnit.
 * VII and IX are tested in PortBindingAndDisposabilityTest (Testcontainers).
 */
class TwelveFactorArchitectureTest {

    private static final String BASE_PACKAGE = "com.example.portsandadapters";

    private static final JavaClasses IMPORTED = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages(BASE_PACKAGE);

    // -------------------------------------------------------------------------
    // II. Dependencies — no OS binaries invoked from Java code
    // -------------------------------------------------------------------------
    @Test
    void factor_ii_no_process_builder() {
        ArchRule rule = noClasses()
                .that().resideInAPackage(BASE_PACKAGE + "..")
                .should().dependOnClassesThat()
                .haveSimpleName("ProcessBuilder")
                .because("Factor II: declare dependencies in pom.xml, "
                        + "do not invoke OS binaries via ProcessBuilder");

        rule.check(IMPORTED);
    }

    @Test
    void factor_ii_no_runtime_exec() {
        ArchRule rule = noClasses()
                .that().resideInAPackage(BASE_PACKAGE + "..")
                .should().accessClassesThat()
                .haveFullyQualifiedName("java.lang.Runtime")
                .because("Factor II: use declared dependencies, "
                        + "not Runtime.exec() for OS tools");

        rule.check(IMPORTED);
    }

    // -------------------------------------------------------------------------
    // IV. Backing services — domain must not import concrete infrastructure
    // -------------------------------------------------------------------------
    @Test
    void factor_iv_domain_no_depends_on_infrastructure() {
        ArchRule rule = noClasses()
                .that().resideInAPackage(BASE_PACKAGE + ".domain..")
                .should().dependOnClassesThat()
                .resideInAPackage(BASE_PACKAGE + ".infrastructure..")
                .because("Factor IV: domain accesses backing services "
                        + "only through ports (interfaces)");

        rule.allowEmptyShould(true).check(IMPORTED);
    }

    @Test
    void factor_iv_domain_no_depends_on_spring_data() {
        ArchRule rule = noClasses()
                .that().resideInAPackage(BASE_PACKAGE + ".domain..")
                .should().dependOnClassesThat()
                .resideInAPackage("org.springframework.data..")
                .because("Factor IV: domain must not depend on Spring Data");

        rule.allowEmptyShould(true).check(IMPORTED);
    }

    @Test
    void factor_iv_domain_no_depends_on_spring_framework() {
        ArchRule rule = noClasses()
                .that().resideInAPackage(BASE_PACKAGE + ".domain..")
                .should().dependOnClassesThat()
                .resideInAPackage("org.springframework..")
                .because("Factor IV: domain must not depend on Spring, "
                        + "only on port interfaces");

        rule.allowEmptyShould(true).check(IMPORTED);
    }

    // -------------------------------------------------------------------------
    // VI. Processes — no mutable static state
    // -------------------------------------------------------------------------
    @Test
    void factor_vi_no_mutable_static_fields() {
        ArchRule rule = classes()
                .that().resideInAPackage(BASE_PACKAGE + "..")
                .should(new ArchCondition<>(
                        "have no non-final static fields (mutable static state)") {
                    @Override
                    public void check(JavaClass item, ConditionEvents events) {
                        for (JavaField field : item.getFields()) {
                            boolean isStatic = field.getModifiers()
                                    .contains(JavaModifier.STATIC);
                            boolean isFinal = field.getModifiers()
                                    .contains(JavaModifier.FINAL);
                            if (isStatic && !isFinal) {
                                events.add(SimpleConditionEvent.violated(field,
                                        "Factor VI: mutable static field in "
                                                + item.getName() + "." + field.getName()));
                            }
                        }
                    }
                });

        rule.check(IMPORTED);
    }

    // -------------------------------------------------------------------------
    // XI. Logs — stdout via standard logger only
    // -------------------------------------------------------------------------
    @Test
    void factor_xi_no_system_out() {
        ArchRule rule = noClasses()
                .that().resideInAPackage(BASE_PACKAGE + "..")
                .should().dependOnClassesThat()
                .haveFullyQualifiedName("java.io.PrintStream")
                .because("Factor XI: logs via SLF4J, never System.out directly");

        rule.check(IMPORTED);
    }

    @Test
    void factor_xi_no_file_writer() {
        ArchRule rule = noClasses()
                .that().resideInAPackage(BASE_PACKAGE + "..")
                .should().dependOnClassesThat()
                .haveFullyQualifiedName("java.io.FileWriter")
                .because("Factor XI: app must not write log files directly");

        rule.check(IMPORTED);
    }

    @Test
    void factor_xi_no_file_output_stream() {
        ArchRule rule = noClasses()
                .that().resideInAPackage(BASE_PACKAGE + "..")
                .should().dependOnClassesThat()
                .haveFullyQualifiedName("java.io.FileOutputStream")
                .because("Factor XI: logs are a stream, not files on disk");

        rule.check(IMPORTED);
    }
}
