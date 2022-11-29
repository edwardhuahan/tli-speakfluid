import React from 'react';

/***STYLES***/
import '../styles/Global.css';
import '../styles/SuggestionCard.css';

/***TYPES***/
import '../types/Types';

/**
 * A suggestion card.
 * @author Kai Zhuang
 * @param suggestion 
 * @param index 
 */
export default function SuggestionCard({suggestion, selectSuggestion} : any) {
  const isExample = typeof suggestion.confidenceScore === 'string'
  return (
    <div className={`suggestionCard ${isExample ? "" : "h"}`} onClick={() => selectSuggestion(suggestion)} 
      style={isExample ? {} : {cursor: 'pointer'}}>
      <p className='subheader' style={{fontSize: '15px', alignSelf: 'flex-start', margin: '3px 5px'}}>Chatbot</p>
      <p className='message receive paragraph' style={{fontSize: '15px', fontWeight: '700'}}>{suggestion.chatBotMessage[0].message.slice(1,-1)}</p>
      <p className='subheader' style={{fontSize: '15px', alignSelf: 'flex-end', margin: '3px 5px'}}>Customer</p>
      <p className='message send paragraph' style={{fontSize: '15px'}}>{suggestion.userMessage[0].message.slice(1,-1)}</p>
      {typeof suggestion.confidenceScore === 'string' ? 
      <div> 
        <p className='header' style={{fontSize: '30px', margin: '10px 0'}}>
          {suggestion.confidenceScore}
        </p>
        <p className='subHeader' style={{fontSize: '18px'}}>
          {suggestion.stepSuggestion}
        </p>
      </div> :
      <div>
        <p className='header' style={{fontSize: '30px', margin: '10px 0'}}>
          Our analysis recommends that you use a {suggestion.stepSuggestion[suggestion.confidenceScore.indexOf(Math.max(...suggestion.confidenceScore))]}.
        </p>
        <p className='subHeader' style={{fontSize: '18px'}}>
          We are {Math.max(...suggestion.confidenceScore)}% confident in this answer. If that isn't satisfactory, click on this
          card for more suggestions.
        </p>
      </div>
      }
    </div>
  )
}