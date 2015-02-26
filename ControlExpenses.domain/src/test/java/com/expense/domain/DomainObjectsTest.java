package com.expense.domain;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.PojoPackage;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.PojoValidator;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.rule.Rule;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.NoNestedClassRule;
import com.openpojo.validation.rule.impl.NoStaticExceptFinalRule;
import com.openpojo.validation.test.Tester;
import com.openpojo.validation.test.impl.DefaultValuesNullTester;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import com.openpojo.validation.utils.ValidationHelper;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * Created by Andres on 13/11/2014.
 */
public class DomainObjectsTest {

    private static final Logger LOG = LoggerFactory.getLogger(DomainObjectsTest.class);

    // The package to test
    private static final String POJO_PACKAGE = "com.expenses.domain.entities";

    private List<PojoClass> pojoClasses;
    private PojoValidator pojoValidator;

    @Before
    public void setup() {
        pojoClasses = PojoClassFactory.getPojoClasses(POJO_PACKAGE, new NonTestClassPackageFilter());

        pojoValidator = new PojoValidator();

        // Create Rules to validate structure for POJO_PACKAGE
        pojoValidator.addRule(new NoPublicNonStaticFinalFieldsRule());
        pojoValidator.addRule(new NoStaticExceptFinalRule());
        pojoValidator.addRule(new GetterMustExistRule());
        pojoValidator.addRule(new NoNestedClassRule());

        // Create Testers to validate behaviour for POJO_PACKAGE
        pojoValidator.addTester(new DefaultValuesNullTester());
        pojoValidator.addTester(new SetterTester());
        pojoValidator.addTester(new GetterTester());
        pojoValidator.addTester(new EqualsTester());
        pojoValidator.addTester(new HashCodeTester());
    }

    @Test
    public void testPojoStructureAndBehavior() {
        LOG.info("Testing [{}] classes", pojoClasses.size());
        for (PojoClass pojoClass : pojoClasses) {
            pojoValidator.runValidation(pojoClass);
        }
    }

    private static class NonTestClassPackageFilter implements PojoClassFilter {

        @Override
        public boolean include(PojoClass pojoClass) {
            return !pojoClass.getName().endsWith(PojoPackage.PACKAGE_DELIMETER + PojoPackage.PACKAGE_INFO) &&
                    !pojoClass.getName().contains("Test") && !pojoClass.getName().contains("Generator");
        }
    }

    private static class NoPublicNonStaticFinalFieldsRule implements Rule {
        @Override
        public void evaluate(PojoClass pojoClass) {
            for (PojoField fieldEntry : pojoClass.getPojoFields()) {
                if (fieldEntry.isPublic() && !fieldEntry.isStatic() && !fieldEntry.isFinal()) {
                    Affirm.fail(String.format("Public fields=[%s] not allowed", fieldEntry));
                }
            }
        }
    }

    private static class EqualsTester implements Tester {
        @Override
        public void run(PojoClass pojoClass) {
            final Object firstClassInstance = ValidationHelper.getBasicInstance(pojoClass);
            final Object secondClassInstance = ValidationHelper.getBasicInstance(pojoClass);
            Affirm.affirmTrue(String.format("[%s] is not equal to [%s]",
                            firstClassInstance, secondClassInstance),
                    firstClassInstance.equals(secondClassInstance)
            );

            Affirm.affirmFalse(String.format("Expected false for [%s] equals null", firstClassInstance),
                    firstClassInstance.equals(null)
            );

            Affirm.affirmTrue(String.format("Expected true for [%s] equals this", firstClassInstance),
                    firstClassInstance.equals(firstClassInstance)
            );

            final Object dummyObject = new Object();
            Affirm.affirmFalse(String.format("Expected false for [%s] equals [%s]", firstClassInstance, dummyObject),
                    firstClassInstance.equals(dummyObject)
            );
        }
    }

    private static class HashCodeTester implements Tester {
        @Override
        public void run(PojoClass pojoClass) {
            final Object firstClassInstance = ValidationHelper.getBasicInstance(pojoClass);
            final Object secondClassInstance = ValidationHelper.getBasicInstance(pojoClass);
            Affirm.affirmTrue(String.format("Hashcode of [%s] is not equal to hashcode of [%s]",
                            firstClassInstance.getClass(), secondClassInstance.getClass()),
                    firstClassInstance.hashCode() == secondClassInstance.hashCode()
            );
        }
    }
}
