/*******************************************************************************
 * Copyright (c) 2012 jboulay.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     jboulay - initial API and implementation
 ******************************************************************************/

package org.talend.designer.cmis.manager;

import org.talend.core.model.process.IContext;
import org.talend.core.model.process.IExternalNode;
import org.talend.designer.cmis.CMISComponent;

public class EditorManager {
	
	private SessionManager sessionManager;
	private TypeDefinitionManager typeDefinitionManager;
	private FunctionManager functionManager;
	private IExternalNode component;

	public EditorManager(IExternalNode component) {
		this.component = component;
		this.sessionManager = new SessionManager(component);
		
		String componentName = (String) component.getElementParameter("COMPONENT_NAME").getValue();
		
		if (componentName.endsWith(CMISComponent.INPUT_COMPONENT_SUFFIX)) {
			this.typeDefinitionManager = new InputTypeDefinitionManager(component, sessionManager);
		}else
		{
			this.typeDefinitionManager = new OutputTypeDefinitionManager(component, sessionManager);
		}
		this.functionManager = new FunctionManager(component, sessionManager);
	}
	
	public IExternalNode getComponent() {
		return component;
	}
	
	public FunctionManager getFunctionManager() {
		return functionManager;
	}
	
	public TypeDefinitionManager getModelManager() {
		return typeDefinitionManager;
	}
	
	public SessionManager getSessionManager() {
		return sessionManager;
	}
	
	public void createSession(IContext context) {
		sessionManager.createSession(context);
	}
	
	public void loadModel()
	{
		typeDefinitionManager.load();
	}

	public void save() {

		typeDefinitionManager.save();
		functionManager.save();
	}
}
