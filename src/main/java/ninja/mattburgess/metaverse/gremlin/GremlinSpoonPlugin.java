/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package ninja.mattburgess.metaverse.gremlin;


import org.pentaho.di.core.gui.SpoonFactory;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.ui.core.dialog.ErrorDialog;
import org.pentaho.di.ui.spoon.ISpoonMenuController;
import org.pentaho.di.ui.spoon.Spoon;
import org.pentaho.di.ui.spoon.SpoonLifecycleListener;
import org.pentaho.di.ui.spoon.SpoonPerspective;
import org.pentaho.di.ui.spoon.SpoonPlugin;
import org.pentaho.di.ui.spoon.SpoonPluginCategories;
import org.pentaho.di.ui.spoon.SpoonPluginInterface;
import org.pentaho.ui.xul.XulDomContainer;
import org.pentaho.ui.xul.XulException;
import org.pentaho.ui.xul.dom.Document;
import org.pentaho.ui.xul.impl.AbstractXulEventHandler;

import java.util.Enumeration;
import java.util.ResourceBundle;

@SpoonPlugin( id = "GremlinSpoonPlugin", image = "" )
@SpoonPluginCategories( { "spoon", "trans-graph" } )
public class GremlinSpoonPlugin extends AbstractXulEventHandler implements SpoonPluginInterface, ISpoonMenuController, SpoonLifecycleListener {

  ResourceBundle bundle = new ResourceBundle() {
    @Override
    public Enumeration<String> getKeys() {
      return null;
    }

    @Override
    protected Object handleGetObject( String key ) {
      return BaseMessages.getString( GremlinSpoonPlugin.class, key );
    }
  };

  public GremlinSpoonPlugin() {

  }

  public String getName() {
    return "gremlinSpoonPlugin"; //$NON-NLS-1$
  }

  @Override
  public void onEvent( SpoonLifeCycleEvent evt ) {
    if ( evt.equals( SpoonLifeCycleEvent.STARTUP ) ) {
      Spoon spoon = ( (Spoon) SpoonFactory.getInstance() );
      spoon.addSpoonMenuController( this );
    }
  }

  @Override
  public void applyToContainer( String category, XulDomContainer container ) throws XulException {
    ClassLoader cl = getClass().getClassLoader();
    container.registerClassLoader( cl );
    if ( category.equals( "spoon" ) || category.equals( "trans-graph" ) ) {
      container.loadOverlay( "spoon_overlays.xul", bundle );
      container.addEventHandler( this );
    }
  }

  @Override
  public SpoonLifecycleListener getLifecycleListener() {
    return this;
  }

  @Override
  public SpoonPerspective getPerspective() {
    return null;
  }

  @Override
  public void updateMenu( Document doc ) {
    // Empty method
  }

  public void showGremlinConsole() {
    final Spoon spoon = Spoon.getInstance();
    try {
      GremlinConsoleDialog gcm = new GremlinConsoleDialog( spoon.getShell() );
      gcm.open();
    } catch ( Exception e ) {
      showErrorDialog( e, "Error with Progress Monitor Dialog", "Error with Progress Monitor Dialog" );
    }
  }

  public void init() {

  }

  /**
   * Show an error dialog
   *
   * @param e       The exception to display
   * @param title   The dialog title
   * @param message The message to display
   */
  private void showErrorDialog( Exception e, String title, String message ) {
    new ErrorDialog( Spoon.getInstance().getShell(), title, message, e );
  }
}
