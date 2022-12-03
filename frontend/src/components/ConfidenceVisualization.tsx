import React from 'react';

/***COMPONENTS***/
import { RingProgress } from '@ant-design/plots';

/**
 * Visualization of the top confidence score in a ring.
 * @author Kai Zhuang
 * @param confidence The percentage of confidence to display on the ring. 
 * @returns A functional component of confidence visualized as a ring
 */
export default function ConfidenceVisualization({confidence} : {confidence: number}) {
  const config = {
    autoFit: true,
    percent: confidence,
    color: ['#474af1', '#E8EDF3'],
    innerRadius: 0.85,
    radius: 0.98,
    statistic: {
      title: {
        style: {
          fontFamily: 'articulat-cf,sans-serif',
          fontWeight: '500', 
          fontStyle: 'normal',
          color: 'black',
          fontSize: '12px',
          lineHeight: '14px',
        },
        formatter: () => 'Confidence',
      },
    },
  };

  return <RingProgress {...config} />;
};