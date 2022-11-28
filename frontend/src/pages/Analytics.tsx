import React, { useState } from 'react';

/***ASSETS***/

/***COMPONENTS***/
import NavBar from '../components/NavBar';
import UploadButton from '../components/UploadButton';
import TranscriptDirectory from '../components/TranscriptDirectory';

/***PAGES***/

/***STYLES***/
import '../styles/Global.css'
import '../styles/Analytics.css'

/***TYPES***/

export default function Analytics() {
  const [transcripts, setTranscripts] = useState<any[]>([])
  const [suggestions, setSuggestions] = useState<any[]>([])

  /**
   * Add the analyzed transcript to the transcripts state after uploading.
   * @param transcript The analyzed transcript.
   */
  function addTranscript(transcript: any): void {
    setTranscripts(transcripts.concat({transcript: transcript}));
  }

  /**
   * Set which suggestions to show according to the selected
   * conversation.
   * @param conversation The selected conversation.
   */
  function selectConversation(conversation: any): void {
    setSuggestions(conversation);
  }

  return (
    <div className='pageWrapper'>
      <NavBar />
      <div className='section' style={{flexDirection: 'row', padding: '0px', width: '100%', height: 'calc(100vh - 66px)'}}>
        <div className='transcriptSection'>
          {transcripts.map((transcript: any, index: number) => 
          <TranscriptDirectory key={index} path={transcript.transcript} index={index} selectConversation={selectConversation}/>)}
          <UploadButton addTranscript={addTranscript}/>
        </div>
        <div className='cardsSection'>
          {suggestions.map((suggestion: any, index: number) => <div key={index}>{suggestion.stepSuggestion}</div>)}
        </div>
        <div className='analysisSection'>
          Test
        </div>
      </div>
    </div>
  )
}