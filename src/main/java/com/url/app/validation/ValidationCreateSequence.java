package com.url.app.validation;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence(value = { Default.class, BasicCreateGroup.class, DBCreateGroup.class })
public interface ValidationCreateSequence {
}