package com.arpc.sotnim;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packages = "com.arpc.sotnim", importOptions = ImportOption.DoNotIncludeTests.class)
public class ProductionArchitectureTest {

        @ArchTest
        static final ArchRule layer_dependencies_are_respected = layeredArchitecture().consideringAllDependencies()

                .layer("Boundary").definedBy("com.arpc.sotnim.*.boundary..")
                .layer("Control").definedBy("com.arpc.sotnim.*.control..")
                .layer("Entity").definedBy("com.arpc.sotnim.*.entity..")

                .whereLayer("Control").mayOnlyBeAccessedByLayers("Boundary")
                .whereLayer("Entity").mayOnlyBeAccessedByLayers("Boundary","Control");
}
