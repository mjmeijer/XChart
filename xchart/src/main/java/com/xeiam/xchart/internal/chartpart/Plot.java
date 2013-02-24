/**
 * Copyright 2011-2013 Xeiam LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xeiam.xchart.internal.chartpart;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import com.xeiam.xchart.Chart;
import com.xeiam.xchart.style.StyleManager.ChartType;

/**
 * @author timmolter
 */
public class Plot implements ChartPart {

  /** parent */
  private final Chart chart;

  /** the bounds */
  private Rectangle bounds;

  private PlotSurface plotSurface;

  private PlotContent plotContent;

  /**
   * Constructor
   * 
   * @param chart
   */
  public Plot(Chart chart) {

    this.chart = chart;
    this.plotSurface = new PlotSurface(this);

  }

  @Override
  public Rectangle getBounds() {

    return bounds;
  }

  @Override
  public void paint(Graphics2D g) {

    bounds = new Rectangle();

    // calculate bounds
    int xOffset = (int) (chart.getAxisPair().getyAxis().getBounds().getX() + chart.getAxisPair().getyAxis().getBounds().getWidth() + (chart.getStyleManager().isyAxisTicksVisible() ? (chart
        .getStyleManager().getPlotPadding() + 1) : 0));
    int yOffset = (int) (chart.getAxisPair().getyAxis().getBounds().getY());
    int width = (int) chart.getAxisPair().getxAxis().getBounds().getWidth();
    int height = (int) chart.getAxisPair().getyAxis().getBounds().getHeight();
    bounds = new Rectangle(xOffset, yOffset, width, height);
    // g.setColor(Color.green);
    // g.draw(bounds);

    plotSurface.paint(g);
    if (getChart().getStyleManager().getChartType() == ChartType.Bar) {
      this.plotContent = new PlotContentBarChart(this);
    } else {
      this.plotContent = new PlotContentLineChart(this);
    }
    plotContent.paint(g);

  }

  @Override
  public Chart getChart() {

    return chart;
  }
}
