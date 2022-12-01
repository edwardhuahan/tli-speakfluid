import React from 'react';

/***STYLES***/
import '../styles/Global.css';
import '../styles/components/SuggestionCard.css';

/***TYPES***/
import '../types/Types';
import { SuggestionCardInput } from '../types/Types';

/**
 * A suggestion card.
 * @author Kai Zhuang
 * @param suggestion 
 * @param index 
 */
export default function SuggestionCard({suggestion, selectSuggestion} : SuggestionCardInput) {
  return (
    <div className={`suggestionCard ${suggestion.example ? "" : "h"}`} onClick={() => selectSuggestion(suggestion)} 
      style={suggestion.example ? {} : {cursor: 'pointer'}}>
      <p className='subheader' style={{fontSize: '15px', alignSelf: 'flex-start', margin: '3px 5px'}}>Chatbot</p>
      <p className='message receive paragraph' style={{fontSize: '15px', fontWeight: '700'}}>{suggestion.chatBotMessage}</p>
      <p className='subheader' style={{fontSize: '15px', alignSelf: 'flex-end', margin: '3px 5px'}}>Customer</p>
      <p className='message send paragraph' style={{fontSize: '15px'}}>{suggestion.userMessage}</p>
      {suggestion.example ? 
      <div> 
        <p className='header' style={{fontSize: '30px', margin: '10px 0'}}>
          {suggestion.stepSuggestions[1]}
        </p>
        <p className='subHeader' style={{fontSize: '18px'}}>
          {suggestion.stepSuggestions[0]}
        </p>
      </div> :
      <div>
        <p className='header' style={{fontSize: '30px', margin: '10px 0'}}>
          We recommend {suggestion.topSuggestion} step.
        </p>
        <p className='subHeader' style={{fontSize: '18px'}}>
          We are {suggestion.topConfidence}% confident in this answer. If that isn't satisfactory, click on this
          card for more suggestions.
        </p>
      </div>
      }
    </div>
  )
}