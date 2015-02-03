/**
 */
package org.eclipse.emf.ecp.view.edapt.util.test.model.b.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecp.view.edapt.util.test.model.a.APackage;
import org.eclipse.emf.ecp.view.edapt.util.test.model.a.impl.APackageImpl;
import org.eclipse.emf.ecp.view.edapt.util.test.model.b.BFactory;
import org.eclipse.emf.ecp.view.edapt.util.test.model.b.BPackage;
import org.eclipse.emf.ecp.view.edapt.util.test.model.c.CPackage;
import org.eclipse.emf.ecp.view.edapt.util.test.model.c.impl.CPackageImpl;
import org.eclipse.emf.ecp.view.edapt.util.test.model.d.DPackage;
import org.eclipse.emf.ecp.view.edapt.util.test.model.d.impl.DPackageImpl;
import org.eclipse.emf.ecp.view.edapt.util.test.model.e.EEPackage;
import org.eclipse.emf.ecp.view.edapt.util.test.model.e.impl.EEPackageImpl;
import org.eclipse.emf.ecp.view.edapt.util.test.model.f.FPackage;
import org.eclipse.emf.ecp.view.edapt.util.test.model.f.impl.FPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * 
 * @generated
 */
public class BPackageImpl extends EPackageImpl implements BPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass bEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry
	 * EPackage.Registry} by the package
	 * package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also
	 * performs initialization of the package, or returns the registered package, if one already exists. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.emf.ecp.view.edapt.util.test.model.b.BPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private BPackageImpl() {
		super(eNS_URI, BFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>
	 * This method is used to initialize {@link BPackage#eINSTANCE} when that field is accessed. Clients should not
	 * invoke it directly. Instead, they should simply access that field to obtain the package. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static BPackage init() {
		if (isInited) {
			return (BPackage) EPackage.Registry.INSTANCE.getEPackage(BPackage.eNS_URI);
		}

		// Obtain or create and register package
		final BPackageImpl theBPackage = (BPackageImpl) (EPackage.Registry.INSTANCE.get(eNS_URI) instanceof BPackageImpl ? EPackage.Registry.INSTANCE
			.get(eNS_URI)
			: new BPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		final FPackageImpl theFPackage = (FPackageImpl) (EPackage.Registry.INSTANCE.getEPackage(FPackage.eNS_URI) instanceof FPackageImpl ? EPackage.Registry.INSTANCE
			.getEPackage(FPackage.eNS_URI)
			: FPackage.eINSTANCE);
		final EEPackageImpl theEEPackage = (EEPackageImpl) (EPackage.Registry.INSTANCE.getEPackage(EEPackage.eNS_URI) instanceof EEPackageImpl ? EPackage.Registry.INSTANCE
			.getEPackage(EEPackage.eNS_URI)
			: EEPackage.eINSTANCE);
		final DPackageImpl theDPackage = (DPackageImpl) (EPackage.Registry.INSTANCE.getEPackage(DPackage.eNS_URI) instanceof DPackageImpl ? EPackage.Registry.INSTANCE
			.getEPackage(DPackage.eNS_URI)
			: DPackage.eINSTANCE);
		final APackageImpl theAPackage = (APackageImpl) (EPackage.Registry.INSTANCE.getEPackage(APackage.eNS_URI) instanceof APackageImpl ? EPackage.Registry.INSTANCE
			.getEPackage(APackage.eNS_URI)
			: APackage.eINSTANCE);
		final CPackageImpl theCPackage = (CPackageImpl) (EPackage.Registry.INSTANCE.getEPackage(CPackage.eNS_URI) instanceof CPackageImpl ? EPackage.Registry.INSTANCE
			.getEPackage(CPackage.eNS_URI)
			: CPackage.eINSTANCE);

		// Create package meta-data objects
		theBPackage.createPackageContents();
		theFPackage.createPackageContents();
		theEEPackage.createPackageContents();
		theDPackage.createPackageContents();
		theAPackage.createPackageContents();
		theCPackage.createPackageContents();

		// Initialize created meta-data
		theBPackage.initializePackageContents();
		theFPackage.initializePackageContents();
		theEEPackage.initializePackageContents();
		theDPackage.initializePackageContents();
		theAPackage.initializePackageContents();
		theCPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theBPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(BPackage.eNS_URI, theBPackage);
		return theBPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getB() {
		return bEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getB_A() {
		return (EReference) bEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getB_C() {
		return (EReference) bEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public BFactory getBFactory() {
		return (BFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package. This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) {
			return;
		}
		isCreated = true;

		// Create classes and their features
		bEClass = createEClass(B);
		createEReference(bEClass, B__A);
		createEReference(bEClass, B__C);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) {
			return;
		}
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		final APackage theAPackage = (APackage) EPackage.Registry.INSTANCE.getEPackage(APackage.eNS_URI);
		final CPackage theCPackage = (CPackage) EPackage.Registry.INSTANCE.getEPackage(CPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(bEClass, org.eclipse.emf.ecp.view.edapt.util.test.model.b.B.class, "B", !IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS);
		initEReference(getB_A(), theAPackage.getA(), null, "a", null, 0, 1,
			org.eclipse.emf.ecp.view.edapt.util.test.model.b.B.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
			!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getB_C(), theCPackage.getC(), null, "c", null, 0, 1,
			org.eclipse.emf.ecp.view.edapt.util.test.model.b.B.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
			!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} // BPackageImpl
