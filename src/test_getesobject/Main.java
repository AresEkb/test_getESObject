package test_getesobject;

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.ocl.pivot.ExpressionInOCL;
import org.eclipse.ocl.pivot.IteratorExp;
import org.eclipse.ocl.pivot.PropertyCallExp;
import org.eclipse.ocl.pivot.utilities.OCL;
import org.eclipse.ocl.xtext.essentialocl.EssentialOCLStandaloneSetup;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Signal;
import org.eclipse.uml2.uml.UMLPackage;

public class Main {

    public static void main(String[] args) {
        final String input = "model/test_assoc_getESObject.uml";
        
        System.out.println("Initialization");
        ResourceSet rs = new ResourceSetImpl();
        
        EssentialOCLStandaloneSetup.doSetup();
        org.eclipse.ocl.pivot.uml.UMLStandaloneSetup.init();
        
        OCL ocl = OCL.newInstance(rs);

        System.out.println("Loading UML model " + input);
        Resource resource = ocl.getResourceSet().getResource(URI.createFileURI(new File(input).getAbsolutePath()), true);

        Model uml = (Model)EcoreUtil.getObjectByType(resource.getContents(), UMLPackage.eINSTANCE.getModel());

        try {
            Signal signal1 = (Signal)uml.getPackagedElement("Signal1");
            System.out.println("\numl: " + signal1);
            org.eclipse.ocl.pivot.Signal asSignal1 = ocl.getMetamodelManager().getASOf(org.eclipse.ocl.pivot.Signal.class, signal1);
            System.out.println("pivot: " + asSignal1);
            ExpressionInOCL expr = ocl.createInvariant(asSignal1, "prop2");
            System.out.println("Expression: " + expr);
            PropertyCallExp prop = (PropertyCallExp)expr.getOwnedBody();
            System.out.println("Prop: " + prop.getReferredProperty().getESObject());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Signal signal1 = (Signal)uml.getPackagedElement("Signal1");
            System.out.println("\numl: " + signal1);
            org.eclipse.ocl.pivot.Signal asSignal1 = ocl.getMetamodelManager().getASOf(org.eclipse.ocl.pivot.Signal.class, signal1);
            System.out.println("pivot: " + asSignal1);
            ExpressionInOCL expr = ocl.createInvariant(asSignal1, "assoc1");
            System.out.println("Expression: " + expr);
            PropertyCallExp prop = (PropertyCallExp)expr.getOwnedBody();
            System.out.println("Prop: " + prop.getReferredProperty().getESObject());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
