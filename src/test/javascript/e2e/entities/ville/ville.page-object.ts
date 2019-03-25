import { element, by, ElementFinder } from 'protractor';

export class VilleComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-ville div table .btn-danger'));
    title = element.all(by.css('jhi-ville div h2#page-heading span')).first();

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

export class VilleUpdatePage {
    pageTitle = element(by.id('jhi-ville-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nomVilleInput = element(by.id('field_nomVille'));
    paysSelect = element(by.id('field_pays'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setNomVilleInput(nomVille) {
        await this.nomVilleInput.sendKeys(nomVille);
    }

    async getNomVilleInput() {
        return this.nomVilleInput.getAttribute('value');
    }

    async paysSelectLastOption() {
        await this.paysSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async paysSelectOption(option) {
        await this.paysSelect.sendKeys(option);
    }

    getPaysSelect(): ElementFinder {
        return this.paysSelect;
    }

    async getPaysSelectedOption() {
        return this.paysSelect.element(by.css('option:checked')).getText();
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

export class VilleDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-ville-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-ville'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
