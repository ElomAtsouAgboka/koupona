import { element, by, ElementFinder } from 'protractor';

export class SousmenuComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-sousmenu div table .btn-danger'));
    title = element.all(by.css('jhi-sousmenu div h2#page-heading span')).first();

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

export class SousmenuUpdatePage {
    pageTitle = element(by.id('jhi-sousmenu-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    sousMenuItemInput = element(by.id('field_sousMenuItem'));
    sousMenuItemImgInput = element(by.id('field_sousMenuItemImg'));
    menuSelect = element(by.id('field_menu'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setSousMenuItemInput(sousMenuItem) {
        await this.sousMenuItemInput.sendKeys(sousMenuItem);
    }

    async getSousMenuItemInput() {
        return this.sousMenuItemInput.getAttribute('value');
    }

    async setSousMenuItemImgInput(sousMenuItemImg) {
        await this.sousMenuItemImgInput.sendKeys(sousMenuItemImg);
    }

    async getSousMenuItemImgInput() {
        return this.sousMenuItemImgInput.getAttribute('value');
    }

    async menuSelectLastOption() {
        await this.menuSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async menuSelectOption(option) {
        await this.menuSelect.sendKeys(option);
    }

    getMenuSelect(): ElementFinder {
        return this.menuSelect;
    }

    async getMenuSelectedOption() {
        return this.menuSelect.element(by.css('option:checked')).getText();
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

export class SousmenuDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-sousmenu-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-sousmenu'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
