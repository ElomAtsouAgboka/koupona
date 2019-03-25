import { element, by, ElementFinder } from 'protractor';

export class PaysComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-pays div table .btn-danger'));
    title = element.all(by.css('jhi-pays div h2#page-heading span')).first();

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

export class PaysUpdatePage {
    pageTitle = element(by.id('jhi-pays-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nomPaysInput = element(by.id('field_nomPays'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setNomPaysInput(nomPays) {
        await this.nomPaysInput.sendKeys(nomPays);
    }

    async getNomPaysInput() {
        return this.nomPaysInput.getAttribute('value');
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

export class PaysDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-pays-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-pays'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
