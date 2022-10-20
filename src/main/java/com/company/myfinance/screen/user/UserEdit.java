package com.company.myfinance.screen.user;

import io.jmix.ui.screen.*;
import com.company.myfinance.entity.User;

@UiController("User.edit")
@UiDescriptor("user-edit.xml")
@EditedEntityContainer("userDc")
public class UserEdit extends StandardEditor<User> {
}