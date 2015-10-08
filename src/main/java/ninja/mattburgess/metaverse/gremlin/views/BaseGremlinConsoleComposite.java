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
package ninja.mattburgess.metaverse.gremlin.views;

import ninja.mattburgess.metaverse.gremlin.event.GremlinConsoleEvent;
import ninja.mattburgess.metaverse.gremlin.event.GremlinConsoleEventListener;
import org.eclipse.swt.widgets.Composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mburgess on 5/18/15.
 */
public abstract class BaseGremlinConsoleComposite extends Composite implements GremlinConsoleEventListener {

  protected List<GremlinConsoleEventListener> listeners;

  public BaseGremlinConsoleComposite( Composite parent, int style ) {
    super( parent, style );
    listeners = new ArrayList<>();
  }

  public void addListener( GremlinConsoleEventListener listener ) {
    listeners.add( listener );
  }

  public void removeListener( GremlinConsoleEventListener listener ) {
    listeners.remove( listener );
  }

  public void notifyListeners( Object o ) {
    // Notify listeners
    for ( GremlinConsoleEventListener listener : listeners ) {
      GremlinConsoleEvent gremlinConsoleEvent = new GremlinConsoleEvent( this, o );
      listener.handleGremlinConsoleEvent( gremlinConsoleEvent );
    }
  }

  public void handleGremlinConsoleEvent( GremlinConsoleEvent event ) {
    if ( event.getSource() != this ) {
      setData( event.getData() );
    }
  }

  // TODO make abstract
  public void clear() {}
}
