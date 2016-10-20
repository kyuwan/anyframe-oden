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
package anyframe.oden.eclipse.core.explorer;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Image;

import anyframe.oden.eclipse.core.OdenTrees.TreeObject;
import anyframe.oden.eclipse.core.OdenTrees.TreeParent;
import anyframe.oden.eclipse.core.messages.UIMessages;
import anyframe.oden.eclipse.core.utils.CommonUtil;
import anyframe.oden.eclipse.core.utils.ImageUtil;

/**
 * Tree label provider for Oden view outline.
 * 
 * @author RHIE Jihwan
 * @version 1.0.0
 * @since 1.0.0 RC1
 *
 */
public  class ExplorerViewLabelProvider extends StyledCellLabelProvider implements ILabelProvider{

	// Icon for the "Servers" root
	private ImageDescriptor _serverRootImageDescriptor = ImageUtil.getImageDescriptor(UIMessages.ODEN_EXPLORER_ExplorerViewLabelProvider_ServersRootIcon);
	private Image _serverRootImage = ImageUtil.getImage(_serverRootImageDescriptor);

	// Icon for the servers
	private ImageDescriptor _serverImageDescriptor = ImageUtil.getImageDescriptor(UIMessages.ODEN_EXPLORER_ExplorerViewLabelProvider_ServerIcon);
	private Image _serverImage = ImageUtil.getImage(_serverImageDescriptor);

	// Icon for the "Build Repositories" root
	private ImageDescriptor _repositoryRootImageDescriptor = ImageUtil.getImageDescriptor(UIMessages.ODEN_EXPLORER_ExplorerViewLabelProvider_BuildRepositoriesRootIcon);
	private Image _repositoryRootImage = ImageUtil.getImage(_repositoryRootImageDescriptor);

	// Icon for the build repositories
	private ImageDescriptor _repositoryImageDescriptor = ImageUtil.getImageDescriptor(UIMessages.ODEN_EXPLORER_ExplorerViewLabelProvider_BuildRepositoryIcon);
	private Image _repositoryImage = ImageUtil.getImage(_repositoryImageDescriptor);

	// Icon for the folder
	private ImageDescriptor _folderImageDescriptor = ImageUtil.getImageDescriptor(UIMessages.ODEN_EXPLORER_ExplorerViewLabelProvider_FolderIcon);
	private Image _folderImage = ImageUtil.getImage(_folderImageDescriptor);

	// Icon for the file
	private ImageDescriptor _fileImageDescriptor = ImageUtil.getImageDescriptor(UIMessages.ODEN_EXPLORER_ExplorerViewLabelProvider_FileIcon);
	private Image _fileImage = ImageUtil.getImage(_fileImageDescriptor);

	public ExplorerViewLabelProvider() {

	}
	/**
	 * Disposes all the images
	 */
	public void dispose() {
		//		super.dispose();
		ImageUtil.disposeImage(UIMessages.ODEN_EXPLORER_ExplorerViewLabelProvider_ServerIcon);
		ImageUtil.disposeImage(UIMessages.ODEN_EXPLORER_ExplorerViewLabelProvider_DefaultServerIcon);
		ImageUtil.disposeImage(UIMessages.ODEN_EXPLORER_ExplorerViewLabelProvider_BuildRepositoryIcon);
		ImageUtil.disposeImage(UIMessages.ODEN_EXPLORER_ExplorerViewLabelProvider_ServersRootIcon);
		ImageUtil.disposeImage(UIMessages.ODEN_EXPLORER_ExplorerViewLabelProvider_BuildRepositoriesRootIcon);
	}

	/**
	 * Gets an image for a specific element type
	 */
	public Image getImage(Object element) {
		TreeParent parent = ((TreeObject) element).getParent();
		String parentName = parent.getName();

		String elementName = ((TreeObject) element).getName();
		if (parentName.equals("") && elementName.equals(UIMessages.ODEN_EXPLORER_ExplorerViewLabelProvider_ServersRootLabel)) { //$NON-NLS-1$
			// root folder image for "Servers"
			return _serverRootImage;
		} else if (parentName.equals("") && elementName.equals(UIMessages.ODEN_EXPLORER_ExplorerViewLabelProvider_BuildRepositoriesRootLabel)) { //$NON-NLS-1$
			// root folder image for "Build Repositories"
			return _repositoryRootImage;
		} else if (parentName.equals(UIMessages.ODEN_EXPLORER_ExplorerViewLabelProvider_ServersRootLabel)){ //$NON-NLS-1$
			// server Icon image
			return _serverImage;
		} else if (parentName.equals(UIMessages.ODEN_EXPLORER_ExplorerViewLabelProvider_BuildRepositoriesRootLabel)){ //$NON-NLS-1$
			// repository Icon image
			return _repositoryImage;
		} else if(element instanceof TreeParent){
			// directory Icon image
			return _folderImage;
		} else {
			return _fileImage;
		}
	}

	/**
	 * Gets a visible text upon the nickname of the element
	 */
	public String getText(Object element) {

		return element.toString();
	}
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub

	}
	@Override
	public void update(ViewerCell cell) {

		Object element= cell.getElement();
		if(element instanceof TreeParent){
			cell.setText(element.toString());
		}else if(element instanceof TreeObject){

			String decoration = getFileInfo(element.toString());
			StyledString styledString= new StyledString(CommonUtil.replaceIgnoreCase(element.toString(), decoration, ""), null);
			styledString.append(" " + decoration, StyledString.DECORATIONS_STYLER);

			cell.setText(styledString.toString());
			cell.setStyleRanges(styledString.getStyleRanges());
		}

		cell.setImage(getImage(element));

		super.update(cell);
	}

	private String getFileInfo(String text) {
		return text.substring(text.indexOf("[20") ) ;
	}


}
