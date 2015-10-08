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

import org.pentaho.di.core.Const;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;

public class History extends LinkedList<String> {

  String historyFile;

  public History() {

  }

  public History( String historyFile ) {
    this.historyFile = historyFile;

    try {
      String line;
      final BufferedReader reader = new BufferedReader( new InputStreamReader( new FileInputStream( historyFile ) ) );
      while ( ( line = reader.readLine() ) != null ) {
        add( line );
      }
      reader.close();
    } catch ( Exception e ) {
      // Do nothing
    }

  }

  private int index = -1;

  @Override
  public boolean add( String s ) {
    if ( Const.isEmpty( s ) ) {
      return false;
    }

    boolean result = super.add( s );
    index = size() - 1;
    return result;
  }

  public String up() {
    return ( index >= 0 ) ? get( index-- ) : null;
  }

  public String down() {
    return ( index >= 0 && index < size() - 1 ) ? this.get( index++ ) : "";
  }

  public void save() {
    save( historyFile );
  }

  public void save( String file ) {
    if ( file != null ) {
      try {
        PrintWriter pw = new PrintWriter( new FileWriter( historyFile, false ) );
        for ( String line : this ) {
          pw.println( line );
        }
        pw.close();
      } catch ( Exception e ) {
        // Hey we tried, no big deal
      }
    }
  }
}
