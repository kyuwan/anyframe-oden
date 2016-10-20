package org.anyframe.oden.dimmension.compile;

import org.anyframe.oden.dimmension.domain.BuildInfo;
import org.apache.tools.ant.Task;

public class CompileBroker extends Task {
	private String projectName;
	
	private String requestId;
	
	//private String requestId="[{requestId:QLARIUS_CR_7,buildId:test}]";
	
	private String srcRoot;
	
	private String webRoot;
	
	private String srcAppd;
	
	private String webAppd;
	
	private String packageType;
	
	private String jarWebRoot;
	
	private String packageName;
	
	private String classesRoot;
	
	private String libsRoot;
	
	private String reference;
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public void setSrcRoot(String srcRoot) {
		this.srcRoot = srcRoot;
	}

	public void setWebRoot(String webRoot) {
		this.webRoot = webRoot;
	}

	public void setSrcAppd(String srcAppd) {
		this.srcAppd = srcAppd;
	}

	public void setWebAppd(String webAppd) {
		this.webAppd = webAppd;
	}	

	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}
	
	public void setJarWebRoot(String jarWebRoot) {
		this.jarWebRoot = jarWebRoot;
	}
	
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public void setClassesRoot(String classesRoot) {
		this.classesRoot = classesRoot;
	}
	
	public void setLibsRoot(String libsRoot) {
		this.libsRoot = libsRoot;
	}
	
	public void setReference(String reference) {
		this.reference = reference;
	}
	
	public void execute(){
		try {
			// 0. transfer vo object
			BuildInfo build = new BuildInfo(projectName,
					convertJson(requestId), srcRoot, webRoot, srcAppd, webAppd, jarWebRoot, packageName, classesRoot, libsRoot, reference);
			
			Compile compile = new Compile(getProject());
		
			// 1. mkdir(build) and compile
			compile.execute(build);
			// 0. copy for resource file
			compile.rscTransfer(build);			
			// 2. transfer web source
			compile.webTransfer(build);
			// 3. transfer backup(class)
			compile.copyBackup(build);
			// 4. transfer reference(class)
			compile.copyReference(build);
			// 5. jar pacakage
			if("jar".equals(packageType) || "war".equals(packageType)){
				//compile.jarRun(build, packageType);
				compile.packageMove(build,packageType);
			}
			
			
			
		} catch (Exception e) {
			getProject().fireBuildFinished(e);
			System.exit(-1);
		}
		
	}
	
	private String convertJson(String request) {
		String trans = request;
		
		trans = trans.replace("[{", "[{\"");
		trans = trans.replace(":", "\":\"");
		trans = trans.replace(",", "\",\"");
		trans = trans.replace("}", "\"}");
		
		trans = trans.replace("\"{", "{\"");
		trans = trans.replace("}\"", "}");
		
		return trans;
	}
}
