import React from 'react';
import { Link } from 'react-router-dom';

/***ASSETS***/
import chatBot from '../assets/chatbot.svg'

/***COMPONENTS***/
import NavBar from '../components/NavBar';

/***PAGES***/

/***STYLES***/
import '../styles/Global.css'
import '../styles/Home.css';

/***TYPES***/

export default function Home() {
  return (
    <div className='pageWrapper'>
      <NavBar />
      <div className='section centerContent hero'>
        <div style={{display: 'flex', flexDirection: 'row'}}>
          <img src={chatBot} alt='Illustration of a chatbot' style={{width: '35%', margin: '30px'}} />
          <div style={{margin: '30px'}}>
            <h1 className='header'>Your 1-click solution for classifying chatbot sentiments.</h1>
            <h2 className='subHeader'>No need to manually classify hundreds of messages anymore. One click, and you're done.</h2>
          </div>
        </div>
      </div>
      <div className='section'>
        
      </div>
      <Link to='/analytics'>Analytics</Link>
    </div>
  )
}
