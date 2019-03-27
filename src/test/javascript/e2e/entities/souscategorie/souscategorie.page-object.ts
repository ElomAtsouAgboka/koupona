import { element, by, ElementFinder } from 'protractor';

export class SouscategorieComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-souscategorie div table .btn-danger'));
    title = element.all(by.css('jhi-souscategorie div h2#page-heading span')).first();

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

export class SouscategorieUpdatePage {
    pageTitle = element(by.id('jhi-souscategorie-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nomSousCategorieInput = element(by.id('field_nomSousCategorie'));
    categorieSelect = element(by.id('field_categorie'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setNomSousCategorieInput(nomSousCategorie) {
        await this.nomSousCategorieInput.sendKeys(nomSousCategorie);
    }

    async getNomSousCategorieInput() {
        return this.nomSousCategorieInput.getAttribute('value');
    }

    async categorieSelectLastOption() {
        await this.categorieSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async categorieSelectOption(option) {
        await this.categorieSelect.sendKeys(option);
    }

    getCategorieSelect(): ElementFinder {
        return this.categorieSelect;
    }

    async getCategorieSelectedOption() {
        return this.categorieSelect.element(by.css('option:checked')).getText();
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

export class SouscategorieDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-souscategorie-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-souscategorie'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
