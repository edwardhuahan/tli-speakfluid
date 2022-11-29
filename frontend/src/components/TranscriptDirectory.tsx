import React, { useState } from 'react';

/***COMPONENTS***/
import { BsChevronRight, BsChevronDown } from 'react-icons/bs';

/***STYLES***/
import '../styles/Global.css';
import '../styles/TranscriptDirectory.css';

/**
 * A function to map each transcript from the transcript state into a list of
 * transcript buttons.
 * 
 * @author Kai Zhuang
 * @param transcript An analyzed transcript from the transcripts state.
 * @returns A transcript button used to view this transcript.
 */
export default function TranscriptDirectory({path, index, selectConversation}: any) {
  const [isToggled, setIsToggled] = useState(false);

  /**
   * Set the clicked conversation to the selected
   * conversation.
   * @author Kai Zhuang
   */
  const onSelectConversation = () => {
    selectConversation(path[Object.keys(path)[0]])
  }

  // If path is an array this means that this is a transcript header. Thus,
  // recurse to render the transcript's associated conversation headers.
  if (Array.isArray(path)) {
    return (
      <div className='transcript' key={index}>
        <div className='can-hover' 
        style={{display: 'flex', flexDirection: 'row', paddingLeft: '30px', paddingTop: '5px', paddingBottom: '5px'}}
        onClick={() => setIsToggled(!isToggled)}>
          {isToggled ? <BsChevronDown/> : <BsChevronRight/>}
          <p className='paragraph' style={{fontSize: '20px', paddingLeft: '5px'}}>Transcript {index + 1}</p>
        </div>
        {
          isToggled && path.map((conversation: any, i: number) => 
          <TranscriptDirectory key={i} path={conversation} index={i} selectConversation={selectConversation}/>)
        }
      </div>
    )
  } 

  // Path is not an array so elements must be conversation headers. Terminate recursion.
  return (
    <div className='conversation can-hover' key={index} onClick={onSelectConversation} 
    style={{paddingTop: '5px', paddingBottom: '5px', paddingLeft: '40px'}}>
        <p style={{fontSize: '20px', paddingLeft: '30px'}} className='paragraph'>Conversation {index + 1}</p>
    </div>
  )
}