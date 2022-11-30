import React, { useState } from 'react';

/***ASSETS***/
import Analyze from '../assets/analyze.webp';
import Analysis from '../assets/analysis.webp';

/***COMPONENTS***/
import NavBar from '../components/NavBar';
import UploadButton from '../components/UploadButton';
import TranscriptDirectory from '../components/TranscriptDirectory';
import SuggestionCard from '../components/SuggestionCard';
import ConfidenceVisualization from '../components/ConfidenceVisualization';

/***STYLES***/
import '../styles/Global.css';
import '../styles/pages/Analytics.css';
import { ChartData, FormattedSuggestion, RawSuggestion } from '../types/Types';
import BulletVisualization from '../components/BulletVisualization';

// A place holder suggestion to show the user how our application works.
const exampleSuggestions: FormattedSuggestion = {
  chatBotMessage: 'The messages sent by your chatbot will appear like this.',
  userMessage: 'The messages sent by your customer will appear like this.',
  topSuggestion: '',
  topConfidence: 0,
  stepSuggestions: ['Click on one of the conversations in your uploaded transcript(s) to see your results.', 
                   'Our best talk step suggestion for the interaction above appears here.'],
  confidenceScores: [],
  data: [],
  example: true
}

// Descriptions of each talk step for the analysis section.
const talkStepDescriptions: any = {
  Choice: 'When designing linear and choice-based conversations in Voice Assistant projects, the Choice step is ideal for pre-defined paths and choices.',
  Button: 'Functioning similar to Choice steps, Buttons are used commonly in Chat Assistant (ie. Chatbot) type projects to present choice paths, options or decision/input points to help progress in the conversation.',
  Capture: 'The Capture Step lets you build dynamic conversation experiences by capturing and recording all or part of a user\'s utterance within a selected variable. This can be used to collect a specific piece of information from your user, such as their name or an email address.',
  Speak: 'The Speak Step is used to make any voice-based project type speak using text-to-speech. This is what your user hears or sees as a response from the voice-based conversation experience.',
  Text: 'Conceptually, you can think of this as it is the replacement of the Speak step for all Chat Assistant projects, found in the Talk section of the Steps Menu.',
  Audio: 'The Audio Step allows you to add short audio files that are less than 240 seconds in length to your voice projects.',
  Image: 'The Image Step allows you to include Images and/or GIFs in your voice and chat projects. For chat-based projects, your visuals will show up as in-line visuals within the chat dialog.',
  Card: 'The Card Step will allow you to provide some on-screen information in-line with the conversational experience & interface, either on a screen-based device or on mobile.',
  Carousel: 'The Carousel Step lets you display a selection or gallery of cards to your end users via a carousel or list.'
}

/**
 * The analytics page where users can upload transcripts, receive our analysis after
 * the server processes the transcript, and view the results of the analysis.
 * @author Kai Zhuang
 * @returns The Analytics page as a functional component.
 */
export default function Analytics() {
  // The list of uploaded transcripts being displayed on the transcriptSection
  const [transcripts, setTranscripts] = useState<any[]>([])
  // The list of suggestions being displayed on the cardSection
  const [suggestions, setSuggestions] = useState<FormattedSuggestion[]>([exampleSuggestions])
  // The selected suggestion being displayed on the analysisSection
  const [suggestion, setSuggestion] = useState<FormattedSuggestion | null>(null)

  /**
   * 
   */
  function formatSuggestion(suggestion: RawSuggestion): FormattedSuggestion {
    const s: FormattedSuggestion = {
      chatBotMessage: suggestion.chatBotMessage[0].message,
      userMessage: suggestion.userMessage[0].message,
      topSuggestion: suggestion.stepSuggestion[suggestion.confidenceScore.indexOf(Math.max(...suggestion.confidenceScore))],
      topConfidence: Math.max(...suggestion.confidenceScore),
      stepSuggestions: suggestion.stepSuggestion,
      confidenceScores: suggestion.confidenceScore,
      data: [],
      example: false
    }

    // Formats the stepSuggestions so they fit the inputs for the bullet graphs.
    const chartData: ChartData[] = []
    suggestion.stepSuggestion.forEach((step: string, index: number) => {
      chartData.push({title: step, 
                      ranges: [40,80,100],
                      Target: [80],
                      Confidence: [suggestion.confidenceScore[index]], 
                    })
    })
    s.data = chartData

    return s
  }

  /**
   * Add the analyzed transcript to the transcripts state after uploading.
   * @author Kai Zhuang
   * @param transcript The analyzed transcript.
   */
  function addTranscript(transcript: any[]): void {
    setTranscripts(transcripts.concat({transcript: transcript}));
  }

  /**
   * Set which suggestions to show according to the selected
   * conversation.
   * @author Kai Zhuang
   * @param conversation The selected conversation.
   */
  function selectConversation(conversation: RawSuggestion[]): void {
    const formattedSuggestions: FormattedSuggestion[] = []

    conversation.slice(1, -1).forEach((suggestion: any) => {
      formattedSuggestions.push(formatSuggestion(suggestion))
    })

    setSuggestions(formattedSuggestions);
  }

  /**
   * Set which suggestion to show on the analysis
   * section according to the selected suggestion.
   * @author Kai Zhuang
   * @param selectedSuggestion The selected suggestion.
   */
  function selectSuggestion(selectedSuggestion: FormattedSuggestion): void {
    setSuggestion(selectedSuggestion);
  }

  return (
    <div className='pageWrapper'>
      <NavBar />
      {transcripts.length === 0 ? 
        <div className='section centerContent uploadTranscriptPage'>
          <div className='analyzeImageContainer'>
            <img className='analyzeImage width100' src={Analyze} alt='Analyze' />
          </div>
          <h1 className='subHeader' style={{fontSize: '20px', marginBottom: '10px', textAlign: 'center'}}>
            Start by uploading a transcript! <br/> {'(JSON only)'}
          </h1>
          <div style={{width: '200px'}}>
            <UploadButton addTranscript={addTranscript}/>
          </div>
        </div> :
        <div className='section' style={{flexDirection: 'row', padding: '0px', width: '100%', height: 'calc(100vh - 66px)'}}>
          <div className='transcriptSection'>
            <UploadButton addTranscript={addTranscript}/>
            {/*Map transcript and its conversations into a transcript directory.*/}
            {transcripts.map((transcript: any, index: number) => 
            <TranscriptDirectory key={index} path={transcript.transcript} index={index} selectConversation={selectConversation}/>)}
          </div>
          <div className='cardsSection'>
            {/*Map suggestions from conversation into suggestion cards.*/}
            {suggestions.map((suggestion: FormattedSuggestion, index: number) => 
            <SuggestionCard key={index} suggestion={suggestion} selectSuggestion={suggestion.example ? () => {} : selectSuggestion}/>)}
          </div>
          <div className='analysisSection'>
            {/*If there is a selected suggestion then show analytics dashboard otherwise motivate user
            to select one.*/}
            {suggestion ? 
              <div>
                <h2 className='subHeader' style={{fontSize: '18px', marginBottom: '10px'}}>Your chatbot's message</h2>
                <h1 className='header' style={{fontSize: '30px'}}>{suggestion.chatBotMessage}</h1>
                  <div className='topSuggestion'>
                    <div style={{height: '100%', width: '150px', alignSelf: 'end', marginRight: '20px'}}>
                      <ConfidenceVisualization confidence={suggestion.topConfidence / 100} />
                    </div>
                    <div style={{display: 'flex', flexDirection: 'column', justifyContent: 'center', width: 'calc(100% - 160px)'}}>
                      <h2 className='subHeader' style={{fontSize: '18px', marginBottom: '10px'}}>Our top suggestion is</h2>
                      <h1 className='header' style={{fontSize: '30px', marginBottom: '10px'}}>
                        {suggestion.topSuggestion}
                      </h1>
                      <h2 className='subHeader' style={{fontSize: '18px'}}>
                        {talkStepDescriptions[suggestion.topSuggestion]}
                      </h2>
                    </div>
                  </div>
                  <div className='otherSuggestions'>
                    <BulletVisualization data={suggestion.data} />
                  </div>
              </div> :
              <div className='centerContent' style={{display: 'flex', flexDirection: 'column', width: '100%', height: '100%'}}>
                <div style={{width: '400px', height: '400px', marginBottom: '-15px'}}>
                <img src={Analysis} alt='Analysis' style={{width: '100%'}} />
              </div>
                <h1 className='subHeader' style={{fontSize: '30px'}}>Select a suggestion to view our analysis!</h1>
              </div>
            }
          </div>
        </div>
      }
    </div>
  )
}