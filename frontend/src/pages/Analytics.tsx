import React, { useState } from 'react';

/***ASSETS***/
import Analyze from '../assets/analyze.webp';
import Analysis from '../assets/analysis.webp';

/***COMPONENTS***/
import NavBar from '../components/NavBar';
import UploadButton from '../components/UploadButton';
import TranscriptDirectory from '../components/TranscriptDirectory';
import SuggestionCard from '../components/SuggestionCard';
import { Liquid, Bullet } from '@ant-design/plots';

/***STYLES***/
import '../styles/Global.css';
import '../styles/Analytics.css';

// A place holder suggestion to show the user how our application works.
const exampleSuggestions = {
  chatBotMessage: [{message: 'xThe messages sent by your chatbot will appear like this.x'}],
  userMessage: [{message: 'xThe messages sent by your customer will appear like this.x'}],
  confidenceScore: 'Our best talk step suggestion for the interaction above appears here.',
  stepSuggestion: 'Click on one of the conversations in your uploaded transcript(s) to see your results.'
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
  const [suggestions, setSuggestions] = useState<any[]>([exampleSuggestions])
  // The selected suggestion being displayed on the analysisSection
  const [suggestion, setSuggestion] = useState<any>(null)

  /**
   * Add the analyzed transcript to the transcripts state after uploading.
   * @author Kai Zhuang
   * @param transcript The analyzed transcript.
   */
  function addTranscript(transcript: any): void {
    setTranscripts(transcripts.concat({transcript: transcript}));
  }

  /**
   * Set which suggestions to show according to the selected
   * conversation.
   * @author Kai Zhuang
   * @param conversation The selected conversation.
   */
  function selectConversation(conversation: any): void {
    setSuggestions(conversation.slice(1, -1));
  }

  /**
   * Set which suggestion to show on the analysis
   * section according to the selected suggestion.
   * @author Kai Zhuang
   * @param suggestion The selected suggestion.
   */
  function selectSuggestion(suggestion: any): void {
    const s = {
      chatBotMessage: suggestion.chatBotMessage[0].message,
      userMessage: suggestion.userMessage[0].message,
      topSuggestion: suggestion.stepSuggestion[suggestion.confidenceScore.indexOf(Math.max(...suggestion.confidenceScore))],
      topConfidence: Math.max(...suggestion.confidenceScore) / 100,
      stepSuggestions: suggestion.stepSuggestion,
      confidenceScores: suggestion.confidenceScore,
      data: [{}]
    }

    // Formats the stepSuggestions so they fit the inputs for the bullet graphs.
    const stepSuggestions: {}[] = []
    
    suggestion.stepSuggestion.forEach((step: String, index: number) => {
      stepSuggestions.push({title: step, 
                            ranges: [40,60,80],
                            target: [80],
                            measures: [suggestion.confidenceScore[index]], 
                          })
    })

    s.data = stepSuggestions

    setSuggestion(s);
  }

  return (
    <div className='pageWrapper'>
      <NavBar />
      {transcripts.length === 0 ? 
        <div className='section centerContent' style={{width: 'calc(100% - 60px)', height: 'calc(100vh - 126px)'}}>
          <div style={{width: '400px', height: '400px', marginBottom: '-15px'}}>
            <img src={Analyze} alt='Analyze' style={{width: '100%'}} />
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
            {transcripts.map((transcript: any, index: number) => 
            <TranscriptDirectory key={index} path={transcript.transcript} index={index} selectConversation={selectConversation}/>)}
          </div>
          <div className='cardsSection'>
            {suggestions.map((suggestion: any, index: number) => 
            <SuggestionCard key={index} suggestion={suggestion} selectSuggestion={typeof suggestion.confidenceScore === 'string' ? () => {} : selectSuggestion}/>)}
          </div>
          <div className='analysisSection'>
            {suggestion ? 
              <div>
                <h2 className='subHeader' style={{fontSize: '18px', marginBottom: '10px'}}>Your chatbot's message</h2>
                <h1 className='header' style={{fontSize: '30px'}}>{suggestion.chatBotMessage}</h1>
                  <div className='topSuggestion'>
                    <div style={{height: '100%', width: '150px', alignSelf: 'end', marginRight: '10px'}}>
                      <Liquid 
                        color='#474af1'
                        percent={suggestion.topConfidence} 
                        outline={{border: 4, distance: 6}}
                        wave={{length: 128}}
                        statistic={{title: {content: 'Confidence', style: {fontFamily: 'articulat-cf,sans-serif',
                                            fontWeight: '500', fontStyle: 'normal'}},
                                    content: {style: {fontFamily: 'articulat-cf,sans-serif',
                                            fontWeight: '500', fontStyle: 'normal'}}}}
                      />
                    </div>
                    <div style={{display: 'flex', flexDirection: 'column', justifyContent: 'center', width: 'calc(100% - 160px)'}}>
                      <h2 className='subHeader' style={{fontSize: '18px', marginBottom: '10px'}}>Our top suggestion is</h2>
                      <h1 className='header' style={{fontSize: '30px', marginBottom: '10px'}}>
                        {suggestion.topSuggestion}
                      </h1>
                      <h2 className='subHeader' style={{fontSize: '18px'}}>
                        The Carousel Step lets you display a selection or gallery of cards to your end users via a carousel or list.
                      </h2>
                    </div>
                  </div>
                  <div className='otherSuggestions'>
                    <Bullet 
                      data={suggestion.data}
                      autoFit={false}
                      measureField='measures'
                      rangeField='ranges'
                      targetField='target'
                      xField='title'
                      color={{range: ['#FFbcb8', '#FFe0b0', '#bfeec8'], measure: '#474af1', target: '#39a3f4',}}
                      label={{measure: {
                        position: 'middle',
                        style: {
                          fill: '#fff',
                        },
                      },}}
                      xAxis={{line: null}}
                      yAxis={false}
                      legend={{
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
                            value: 'Confidence',
                            name: 'Confidence',
                            marker: {
                              symbol: 'square',
                              style: {
                                fill: '#474af1',
                                r: 5,
                              },
                            },
                          },
                          {
                            value: 'Desired',
                            name: 'Desired',
                            marker: {
                              symbol: 'line',
                              style: {
                                stroke: '#39a3f4',
                                r: 5,
                              },
                            },
                          },
                        ],
                      }}
                    />
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