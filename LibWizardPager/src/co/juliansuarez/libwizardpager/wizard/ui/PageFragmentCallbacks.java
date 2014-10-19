package co.juliansuarez.libwizardpager.wizard.ui;

import co.juliansuarez.libwizardpager.wizard.model.Page;

public interface PageFragmentCallbacks {
    Page onGetPage(String key);
    void setCountText(String text,int index);
}
