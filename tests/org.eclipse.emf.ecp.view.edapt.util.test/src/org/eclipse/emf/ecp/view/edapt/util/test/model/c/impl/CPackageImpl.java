/**
 */
package org.eclipse.emf.ecp.view.edapt.util.test.model.c.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecp.view.edapt.util.test.model.a.APackage;
import org.eclipse.emf.ecp.view.edapt.util.test.model.a.impl.APackageImpl;
import org.eclipse.emf.ecp.view.edapt.util.test.model.b.BPackage;
import org.eclipse.emf.ecp.view.edapt.util.test.model.b.impl.BPackageImpl;
import org.eclipse.emf.ecp.view.edapt.util.test.model.c.CFactory;
import org.eclipse.emf.ecp.view.edapt.util.test.model.c.CPackage;
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
public class CPackageImpl extends EPackageImpl implements CPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass cEClass = null;

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
	 * @see org.eclipse.emf.ecp.view.edapt.util.test.model.c.CPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private CPackageImpl() {
		super(eNS_URI, CFactory.eINSTANCE);
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
	 * This method is used to initialize {@link CPackage#eINSTANCE} when that field is accessed. Clients should not
	 * invoke it directly. Instead, they should simply access that field to obtain the package. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static CPackage init() {
		if (isInited) {
			return (CPackage) EPackage.Registry.INSTANCE.getEPackage(CPackage.eNS_URI);
		}

		// Obtain or create and register package
		final CPackageImpl theCPackage = (CPackageImpl) (EPackage.Registry.INSTANCE.get(eNS_URI) instanceof CPackageImpl ? EPackage.Registry.INSTANCE
			.get(eNS_URI)
			: new CPackageImpl());

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
		final BPackageImpl theBPackage = (BPackageImpl) (EPackage.Registry.INSTANCE.getEPackage(BPackage.eNS_URI) instanceof BPackageImpl ? EPackage.Registry.INSTANCE
			.getEPackage(BPackage.eNS_URI)
			: BPackage.eINSTANCE);
		final APackageImpl theAPackage = (APackageImpl) (EPackage.Registry.INSTANCE.getEPackage(APackage.eNS_URI) instanceof APackageImpl ? EPackage.Registry.INSTANCE
			.getEPackage(APackage.eNS_URI)
			: APackage.eINSTANCE);

		// Create package meta-data objects
		theCPackage.createPackageContents();
		theFPackage.createPackageContents();
		theEEPackage.createPackageContents();
		theDPackage.createPackageContents();
		theBPackage.createPackageContents();
		theAPackage.createPackageContents();

		// Initialize created meta-data
		theCPackage.initializePackageContents();
		theFPackage.initializePackageContents();
		theEEPackage.initializePackageContents();
		theDPackage.initializePackageContents();
		theBPackage.initializePackageContents();
		theAPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theCPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(CPackage.eNS_URI, theCPackage);
		return theCPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EClass getC() {
		return cEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EReference getC_D() {
		return (EReference) cEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public CFactory getCFactory() {
		return (CFactory) getEFactoryInstance();
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
		cEClass = createEClass(C);
		createEReference(cEClass, C__D);
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
		final DPackage theDPackage = (DPackage) EPackage.Registry.INSTANCE.getEPackage(DPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(cEClass, org.eclipse.emf.ecp.view.edapt.util.test.model.c.C.class, "C", !IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS);
		initEReference(getC_D(), theDPackage.getD(), null, "d", null, 0, 1,
			org.eclipse.emf.ecp.view.edapt.util.test.model.c.C.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
			!IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} // CPackageImpl
