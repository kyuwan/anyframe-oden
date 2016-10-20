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
package anyframe.oden.eclipse.core.editors.actions;

import anyframe.oden.eclipse.core.OdenMessages;
import anyframe.oden.eclipse.core.editors.TaskPage;
import anyframe.oden.eclipse.core.history.AbstractDeploymentHistoryViewAction;
import anyframe.oden.eclipse.core.history.DeploymentHistoryView;

/**
 * The Implementation of RefreshAction,
 * for the Anyframe Oden Deployment History view.
 * 
 * @author HONG Junghwan
 * @version 1.0.0
 * @since 1.0.0 RC1
 *
 */
public class TaskRefreshAction extends AbstractDeploymentHistoryViewAction {

	public TaskRefreshAction() {
		super(
				OdenMessages.ODEN_SNAPSHOT_Actions_RefreshAction_Refresh,
				OdenMessages.ODEN_SNAPSHOT_Actions_RefreshAction_RefreshTooltip,
				OdenMessages.ODEN_SNAPSHOT_Actions_RefreshAction_RefreshIcon);
	}

	public void run() {
		new TaskPage();
		TaskPage.loadInitData();
		TaskPage.loadInitPolicyData();
		TaskPage.taskViewer.refresh();
		TaskPage.runViewer.refresh();
	}

}