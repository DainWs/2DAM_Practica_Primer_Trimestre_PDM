package com.joseduarte.practicafinalprimertrimestres.appliers;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.joseduarte.practicafinalprimertrimestres.models.Models;

public interface Applier {
    public void applyTo(View view, Models models);
}
