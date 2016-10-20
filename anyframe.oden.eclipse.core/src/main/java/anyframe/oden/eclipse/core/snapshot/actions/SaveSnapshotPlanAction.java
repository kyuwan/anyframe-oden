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
package anyframe.oden.eclipse.core.snapshot.actions;

import org.eclipse.jface.dialogs.MessageDialog;

import anyframe.oden.eclipse.core.OdenActivator;
import anyframe.oden.eclipse.core.OdenException;
import anyframe.oden.eclipse.core.OdenMessages;
import anyframe.oden.eclipse.core.snapshot.AbstractSnapshotViewAction;
import anyframe.oden.eclipse.core.snapshot.SnapshotStatusProgress;
import anyframe.oden.eclipse.core.snapshot.SnapshotView;
import anyframe.oden.eclipse.core.snapshot.SnapshotViewContentProvider;
import anyframe.oden.eclipse.core.utils.DialogUtil;

/**
 * Edit an existing plan action in the Snapshot view. This class extends
 * AbstractSnapshotViewAction class.
 * 
 * @author LEE Sujeong
 * @version 1.0.0 RC2
 * 
 */
public class SaveSnapshotPlanAction extends AbstractSnapshotViewAction {

	/**
	 * Constructor of SaveSnapshotPlanAction forward actionId,
	 * actionTooltipText, actionIconId to AbstractSnapshotViewAction
	 */
	public SaveSnapshotPlanAction() {
		super(
				OdenMessages.ODEN_SNAPSHOT_Actions_SaveSnapshotPlanAction_NewPlan,
				OdenMessages.ODEN_SNAPSHOT_Actions_SaveSnapshotPlanAction_NewPlanTooltip,
				OdenMessages.ODEN_SNAPSHOT_Actions_SaveSnapshotPlanAction_NewPlanIcon);
	}

	/**
	 * Run this Action with message box
	 */
	public void run() {
		Object selected = OdenActivator.getDefault().getSnapshotView()
				.getSelected();
		if (selected == null) {
			DialogUtil.openMessageDialog(
					OdenMessages.ODEN_SNAPSHOT_Actions_MsgInfoAddPlan,
					OdenMessages.ODEN_SNAPSHOT_Actions_SelectSnapshotPlanSave,
					MessageDialog.INFORMATION);
		} else {

			String selection = OdenActivator.getDefault().getSnapshotView()
					.getSelected()[0].toString();
			String target = SnapshotView.targetLocation;
			String destAgent = SnapshotView.destAgent;
			String dest = SnapshotView.destination;
			String desc = SnapshotView.description;

			String msg = OdenMessages.ODEN_SNAPSHOT_Dialogs_NewPlanCmdHead
					+ "\"" //$NON-NLS-1$
					+ selection
					+ "\"" + " " + OdenMessages.ODEN_SNAPSHOT_Dialogs_NewPlanCmdSource + " /" //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-6$
					+ target
					+ " " + OdenMessages.ODEN_SNAPSHOT_Dialogs_NewPlanCmdDest + " " + destAgent + "/" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
					+ dest
					+ " " + OdenMessages.ODEN_SNAPSHOT_Dialogs_NewPlanCmdDesc + " " + "\"" + desc + "\"" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
					+ " -json"; //$NON-NLS-1$

			//		String url = "http://" //$NON-NLS-1$
			// + OdenActivator.getDefault().getAliasManager()
			//						.getAgentManager().getAgent(agent).getUrl() + "/shell"; //$NON-NLS-1$

			if (DialogUtil.confirmMessageDialog(
					OdenMessages.ODEN_CommonMessages_Title_ConfirmSave,
					OdenMessages.ODEN_SNAPSHOT_Actions_MsgDlgAlertSavePlan)) {
				// SnapshotViewContentProvider.doOdenBroker(url, msg);
				try {
					 SnapshotViewContentProvider.doOdenBroker(SnapshotView.SHELL_URL,
					 msg);
				} catch (OdenException e) {
					OdenActivator
							.error(
									OdenMessages.ODEN_SNAPSHOT_Actions_Exception_SaveSnapshotPlan,
									e);
					SnapshotView.clearComposite();
				}
			}

			SnapshotView.refreshTree();
		}
	}

	/**
	 * Available check this method
	 */
	public boolean isAvailable() {
		if (getView() == null) {
			return false;
		}

		return true;
	}

}