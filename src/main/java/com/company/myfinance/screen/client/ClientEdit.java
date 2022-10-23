package com.company.myfinance.screen.client;

import io.jmix.ui.screen.*;
import com.company.myfinance.entity.Client;

@UiController("Client.edit")
@UiDescriptor("client-edit.xml")
@EditedEntityContainer("clientDc")
public class ClientEdit extends StandardEditor<Client> {
}