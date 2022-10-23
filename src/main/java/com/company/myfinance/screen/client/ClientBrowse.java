package com.company.myfinance.screen.client;

import io.jmix.ui.screen.*;
import com.company.myfinance.entity.Client;

@UiController("Client.browse")
@UiDescriptor("client-browse.xml")
@LookupComponent("clientsTable")
public class ClientBrowse extends StandardLookup<Client> {
}