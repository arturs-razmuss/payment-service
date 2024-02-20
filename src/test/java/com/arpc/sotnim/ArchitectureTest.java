package com.arpc.sotnim;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

@AnalyzeClasses(packages = "com.arpc.sotnim")
public class ArchitectureTest {

    @ArchTest
    static final ArchRule component_tests_should_be_isolated_from_production_code =
            noClasses().that().resideInAPackage("..component_tests..")
                    .should().dependOnClassesThat().resideInAPackage("..boundary..")
                    .orShould().dependOnClassesThat().haveNameMatching(".*\\.control\\.(?!.*TestDouble$).*")
                    .orShould().dependOnClassesThat().resideInAPackage("..entity..");

    @ArchTest
    static final ArchRule domain_slices_should_be_independant =
            slices().matching("com.arpc.sotnim.(*).entity..").namingSlices("Domain $1")
                    .as("Domains").should().notDependOnEachOther();
}