import React from 'react';

/***COMPOENTS***/
import { Bullet } from '@ant-design/plots';

/***TYPES***/
import { ChartData } from '../types/Types';

/**
 * Visualization of all non 0 confidence score step suggestions in 
 * a bullet chart.
 * @author Kai Zhuang
 * @param data A list of ChartData that contains the data of step suggestions and their confidences.
 * @returns A functional component of step suggestions visualized as a bullet chart.
 */
export default function BulletVisualization({data} : {data: ChartData | any}) {
  const config : any = {
    data,
    measureField: 'Confidence',
    rangeField: 'ranges',
    targetField: 'Target',
    xField: 'title',
    color: {
      range: ['#FFbcb8', '#FFe0b0', '#bfeec8'],
      measure: '#474af1',
      target: '#39a3f4',
    },
    label: {
      measure: {
        position: 'middle',
        style: {
          fill: '#fff',
        },
      },
    },
    xAxis: {
      line: null,
    },
    yAxis: false,
    legend: {
      custom: true,
      position: 'bottom',
      items: [
        {
          value: 'Poor',
          name: 'Poor',
          marker: {
            symbol: 'square',
            style: {
              fill: '#FFbcb8',
              r: 5,
            },
          },
        },
        {
          value: 'Moderate',
          name: 'Moderate',
          marker: {
            symbol: 'square',
            style: {
              fill: '#FFe0b0',
              r: 5,
            },
          },
        },
        {
          value: 'Good',
          name: 'Good',
          marker: {
            symbol: 'square',
            style: {
              fill: '#bfeec8',
              r: 5,
            },
          },
        },
        {
          value: 'Actual Confidence',
          name: 'Actual Confidence',
          marker: {
            symbol: 'square',
            style: {
              fill: '#474af1',
              r: 5,
            },
          },
        },
        {
          value: 'Target Confidence',
          name: 'Target Confidence',
          marker: {
            symbol: 'line',
            style: {
              stroke: '#39a3f4',
              r: 5,
            },
          },
        },
      ],
    },
  };
  
  return <Bullet {...config} />;
};