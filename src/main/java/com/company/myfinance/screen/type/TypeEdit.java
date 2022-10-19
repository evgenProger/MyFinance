package com.company.myfinance.screen.type;

import io.jmix.ui.screen.*;
import com.company.myfinance.entity.Type;

@UiController("Type_.edit")
@UiDescriptor("type-edit.xml")
@EditedEntityContainer("typeDc")
public class TypeEdit extends StandardEditor<Type> {
}