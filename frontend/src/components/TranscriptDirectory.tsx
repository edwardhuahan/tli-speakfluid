import React, { useState } from 'react';

/***ASSETS***/

/***COMPONENTS***/

/***PAGES***/

/***STYLES***/
import '../styles/Global.css'

/***TYPES***/

/**
 * A function to map each transcript from the transcript state into a list of
 * transcript buttons.
 * @param transcript An analyzed transcript from the transcripts state.
 * @returns A transcript button used to view this transcript.
 */
export default function TranscriptDirectory({path, index, selectConversation}: any) {
  const [isToggled, setIsToggled] = useState(false);

  if (Array.isArray(path)) {
    return (
      <div key={index}>
        <p onClick={() => setIsToggled(!isToggled)}>Transcript {index + 1}</p><br/>
        {
          isToggled && path.map((conversation: any, i: number) => 
          <TranscriptDirectory key={i} path={conversation} index={i} selectConversation={selectConversation}/>)
        }
      </div>
    )
  } 

  const onSelectConversation = () => {
    selectConversation(path[Object.keys(path)[0]])
  }

  return (
    <div key={index}>
        <p onClick={onSelectConversation}>Conversation {index + 1}</p><br/>
    </div>
  )
}