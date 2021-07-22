package io.github.yarnshop.demo.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import io.github.yarnshop.demo.ui.views.productlist.ProductView;
import io.github.yarnshop.demo.ui.views.productyarn.ProductYarnView;
import io.github.yarnshop.demo.ui.views.storebox.StoreBoxView;
import io.github.yarnshop.demo.ui.views.studiobox.StudioBoxView;
import io.github.yarnshop.demo.ui.views.supplier.SupplierView;
import io.github.yarnshop.demo.ui.views.yarnalternative.YarnAlternativeView;
import io.github.yarnshop.demo.ui.views.yarnlist.YarnView;
import io.github.yarnshop.demo.ui.views.yarnproduct.YarnProductView;

@PWA(
    name = "Yarn Shop",
    shortName = "CRM",
    offlineResources = {
        "./styles/offline.css",
        "./images/offline.png"
    },
    enableInstallPrompt = false
)
@CssImport("./styles/shared-styles.css")
public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 logo = new H1("Yarn Shop");
        logo.addClassName("logo");

        Anchor logout = new Anchor("/logout", "Log out");

        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logout);
        header.addClassName("header");
        header.setWidth("100%");
        header.expand(logo);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);

        addToNavbar(header);
    }

    private void createDrawer() {
        RouterLink listLink = new RouterLink("Yarn", YarnView.class, "");

        listLink.setHighlightCondition(HighlightConditions.locationPrefix());

        addToDrawer(new VerticalLayout(
                listLink,
                new RouterLink("Yarn - Alternative", YarnAlternativeView.class),
                new RouterLink("Yarn - Product", YarnProductView.class),
                new RouterLink("Product", ProductView.class),
                new RouterLink("Product - Yarn", ProductYarnView.class),

                new RouterLink("Boxes in Studio", StudioBoxView.class),
                new RouterLink("Boxes in Store", StoreBoxView.class),
                new RouterLink("Suppliers", SupplierView.class)
        ));
    }
}
