import { element, by, ElementFinder } from 'protractor';

export class MenuComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-menu div table .btn-danger'));
    title = element.all(by.css('jhi-menu div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class MenuUpdatePage {
    pageTitle = element(by.id('jhi-menu-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    menuItemInput = element(by.id('field_menuItem'));
    menuItemImgInput = element(by.id('field_menuItemImg'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setMenuItemInput(menuItem) {
        await this.menuItemInput.sendKeys(menuItem);
    }

    async getMenuItemInput() {
        return this.menuItemInput.getAttribute('value');
    }

    async setMenuItemImgInput(menuItemImg) {
        await this.menuItemImgInput.sendKeys(menuItemImg);
    }

    async getMenuItemImgInput() {
        return this.menuItemImgInput.getAttribute('value');
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class MenuDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-menu-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-menu'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
