import { element, by, ElementFinder } from 'protractor';

export class QuartierComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-quartier div table .btn-danger'));
    title = element.all(by.css('jhi-quartier div h2#page-heading span')).first();

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

export class QuartierUpdatePage {
    pageTitle = element(by.id('jhi-quartier-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nomQuartierInput = element(by.id('field_nomQuartier'));
    villeSelect = element(by.id('field_ville'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setNomQuartierInput(nomQuartier) {
        await this.nomQuartierInput.sendKeys(nomQuartier);
    }

    async getNomQuartierInput() {
        return this.nomQuartierInput.getAttribute('value');
    }

    async villeSelectLastOption() {
        await this.villeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async villeSelectOption(option) {
        await this.villeSelect.sendKeys(option);
    }

    getVilleSelect(): ElementFinder {
        return this.villeSelect;
    }

    async getVilleSelectedOption() {
        return this.villeSelect.element(by.css('option:checked')).getText();
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

export class QuartierDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-quartier-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-quartier'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
