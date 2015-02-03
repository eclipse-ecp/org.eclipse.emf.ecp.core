/**
 */
package org.eclipse.emf.ecp.view.edapt.util.test.model.d.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecp.view.edapt.util.test.model.a.APackage;
import org.eclipse.emf.ecp.view.edapt.util.test.model.a.impl.APackageImpl;
import org.eclipse.emf.ecp.view.edapt.util.test.model.b.BPackage;
import org.eclipse.emf.ecp.view.edapt.util.test.model.b.impl.BPackageImpl;
import org.eclipse.emf.ecp.view.edapt.util.test.model.c.CPackage;
import org.eclipse.emf.ecp.view.edapt.util.test.model.c.impl.CPackageImpl;
import org.eclipse.emf.ecp.view.edapt.util.test.model.d.DFactory;
import org.eclipse.emf.ecp.view.edapt.util.test.model.d.DPackage;
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
public class DPackageImpl extends EPackageImpl implements DPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass dEClass = null;

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
	 * @see org.eclipse.emf.ecp.view.edapt.util.test.model.d.DPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private DPackageImpl() {
		super(eNS_URI, DFactory.eINSTANCE);
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
	 * This method is used to initialize {@link DPackage#eINSTANCE} when that field is accessed. Clients should not
	 * invoke it directly. Instead, they should simply access that field to obtain the package. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static DPackage init() {
		if (isInited) {
			return (DPackage) EPackage.Registry.INSTANCE.getEPackage(DPackage.eNS_URI);
		}

		// Obtain or create and register package
		final DPackageImpl theDPackage = (DPackageImpl) (EPackage.Registry.INSTANCE.get(eNS_URI) instanceof DPackageImpl ? EPackage.Registry.INSTANCE
			.get(eNS_URI)
			: new DPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		final FPackageImpl theFPackage = (FPackageImpl) (EPackage.Registry.INSTANCE.getEPackage(FPackage.eNS_URI) instanceof FPackageImpl ? EPackage.Registry.INSTANCE
			.getEPackage(FPackage.eNS_URI)
			: FPackage.eINSTANCE);
		final EEPackageImpl theEEPackage = (EEPackageImpl) (EPackage.Registry.INSTANCE.getEPackage(EEPackage.eNS_URI) instanceof EEPackageImpl ? EPackage.Registry.INSTANCE
			.getEPackage(EEPackage.eNS_URI)
			: EEPackage.eINSTANCE);
		final BPackageImpl theBPackage = (BPackageImpl) (EPackage.Registry.INSTANCE.getEPackage(BPackage.eNS_URI) instanceof BPackageImpl ? EPackage.Registry.INSTANCE
			.getEPackage(BPackage.eNS_URI)
			: BPackage.eINSTANCE);
		final APackageImpl theAPackage = (APackageImpl) (EPackage.Registry.INSTANCE.getEPackage(APackage.eNS_URI) instanceof APackageImpl ? EPackage.Registry.INSTANCE
			.getEPackage(APackage.eNS_URI)
			: APackage.eINSTANCE);
		final CPackageImpl theCPackage = (CPackageImpl) (EPackage.Registry.INSTANCE.getEPackage(CPackage.eNS_URI) instanceof CPackageImpl ? EPackage.Registry.INSTANCE
			.getEPackage(CPackage.eNS_URI)
			: CPackage.eINSTANCE);

		// Create package meta-data objects
		theDPackage.createPackageContents();
		theFPackage.createPackageContents();
		theEEPackage.createPackageContents();
		theBPackage.createPackageContents();
		theAPackage.createPackageContents();
		theCPackage.createPackageContents();

		// Initialize created meta-data
		theDPackage.initializePackageContents();
		theFPackage.initializePackageContents();
		theEEPackage.initializePackageContents();
		theBPackage.initializePackageContents();
		theAPackage.initializePackageContents();
		theCPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theDPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(DPackage.eNS_URI, theDPackage);
		return theDPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getD() {
		return dEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getD_B() {
		return (EReference) dEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public DFactory getDFactory() {
		return (DFactory) getEFactoryInstance();
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
		dEClass = createEClass(D);
		createEReference(dEClass, D__B);
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
		final BPackage theBPackage = (BPackage) EPackage.Registry.INSTANCE.getEPackage(BPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(dEClass, org.eclipse.emf.ecp.view.edapt.util.test.model.d.D.class, "D", !IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS);
		initEReference(getD_B(), theBPackage.getB(), null, "b", null, 0, 1,
			org.eclipse.emf.ecp.view.edapt.util.test.model.d.D.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
			!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} // DPackageImpl
