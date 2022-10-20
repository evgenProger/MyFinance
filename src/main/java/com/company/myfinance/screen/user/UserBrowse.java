package com.company.myfinance.screen.user;

import io.jmix.ui.screen.*;
import com.company.myfinance.entity.User;

@UiController("User.browse")
@UiDescriptor("user-browse.xml")
@LookupComponent("usersTable")
public class UserBrowse extends StandardLookup<User> {
}