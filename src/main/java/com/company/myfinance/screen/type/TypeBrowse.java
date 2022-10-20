package com.company.myfinance.screen.type;

import io.jmix.ui.screen.*;
import com.company.myfinance.entity.Type;

@UiController("Type_.browse")
@UiDescriptor("type-browse.xml")
@LookupComponent("typesTable")
public class TypeBrowse extends StandardLookup<Type> {
}