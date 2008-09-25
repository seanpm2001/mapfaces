/*
 *    Mapfaces - 
 *    http://www.mapfaces.org
 *
 *    (C) 2007 - 2008, Geomatys
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General 
 *    License as published by the Free Software Foundation; either
 *    version 3 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General  License for more details.
 */

package org.mapfaces.models;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashMap;

/**
 *
 * @author Olivier Terral.
 * @author Mehdi Sidhoum.
 */
 interface Context extends Serializable {

   
      String getId();
    
      void setId(String id);
    
      String getVersion();
    
      void setVersion(String version);

      String getTitle();
    
      void setTitle(String title);

      String getWindowWidth();

      void setWindowWidth(BigInteger windowWidth);

      String getWindowHeight();

      void setWindowHeight(BigInteger windowHeight);

      String getMinx();

      void setMinx(Double minx);

      String getMiny();

      void setMiny(Double miny);

      String getMaxx();

      void setMaxx(Double maxx);

      String getMaxy();

      void setMaxy(Double maxy);

      String getSrs();

      void setSrs(String srs);
      
/*********************************** Layers functions*******************************/
      Layer[] getLayers();

      void setLayers(Layer[] layers);
      
      Layer[] getHiddenLayers();

      Layer[] getVisibleLayers();
      
      Layer[] getQueryableLayers();
      
      Layer[] getNoQueryableLayers();
      
      Layer[] getGroupLayers(String nameOfGroup);
      
      
/*********************************** Layer functions*******************************/
      Layer getLayerFromId(String id);
    
      void addLayer(Layer layer);
    
      void removeLayerFromId(String layerId);
      
 
/*********************************** LayersId functions*******************************/   
/*
 * These functions are used to pass all the layers ids to the javascript
 * */
     String getLayersId();
      
     String getHiddenLayersId();

     String getVisibleLayersId();
      
     String getQueryableLayersId();
      
     String getNoQueryableLayersId();
      
     String getGroupLayersId(String nameOfGroup);
     
      
 /*********************************** Servers functions*******************************/
      HashMap<String, Server> getWmsServers();

      void setWmsServers(HashMap<String, Server> servers);

      HashMap<String, Server> getWfsServers();

      void setWfsServers(HashMap<String, Server> servers);
      
      String save();    

}
