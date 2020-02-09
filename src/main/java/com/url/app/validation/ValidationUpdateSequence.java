package com.url.app.validation;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence(value = { Default.class, BasicUpdateGroup.class, DBUpdateGroup.class })
public interface ValidationUpdateSequence {
}