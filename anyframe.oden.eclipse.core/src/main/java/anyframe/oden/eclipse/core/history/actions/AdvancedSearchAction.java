/*
 * Copyright 2009 SAMSUNG SDS Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package anyframe.oden.eclipse.core.history.actions;

import org.eclipse.ui.PlatformUI;

import anyframe.oden.eclipse.core.OdenActivator;
import anyframe.oden.eclipse.core.OdenMessages;
import anyframe.oden.eclipse.core.history.AbstractDeploymentHistoryViewAction;
import anyframe.oden.eclipse.core.history.dialogs.AdvancedSearchDialog;

/**
 * The Implementation of Advanced Search,
 * for the Anyframe Oden Deployment History view.
 * 
 * @author RHIE Jihwan
 * @version 1.0.0
 * @since 1.0.0 RC1
 *
 */
public class AdvancedSearchAction extends AbstractDeploymentHistoryViewAction {

	public AdvancedSearchAction() {
		super(OdenMessages.ODEN_HISTORY_Actions_AdvancedSearchAction_AdvancedSearch, OdenMessages.ODEN_HISTORY_Actions_AdvancedSearchAction_AdvancedSearchToolTip, OdenMessages.ODEN_HISTORY_Actions_AdvancedSearchAction_AdvancedSearchIcon);
	}

	public void run() {
		AdvancedSearchDialog dialog = new AdvancedSearchDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), getView());
		dialog.open();
	}

}
