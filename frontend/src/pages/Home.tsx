import React from 'react';
import { Link } from 'react-router-dom';

/***ASSETS***/
import chatBot from '../assets/cb.webp'

/***COMPONENTS***/
import NavBar from '../components/NavBar';

/***STYLES***/
import '../styles/Global.css'
import '../styles/pages/Home.css';

/**
 * The landing page where SpeakFluid's product is described.
 * @author Kai Zhuang
 * @returns The Home page as a functional component.
 */
export default function Home() {
  return (
    <div className='pageWrapper homeWrapper'>
      <NavBar />
      <div className='section centerContent hero'>
        <div className='flexRow'>
          <div className='chatBotImageContainer'>
            <img className='chatBotImage' src={chatBot} alt='Illustration of a chatbot' />
          </div>
          <div className='centerContent textContainer'>
            <h1 className='header spaceBottom'>Your 1-click solution for classifying chatbot sentiments.</h1>
            <h2 className='subHeader spaceBottom'>
              No need to manually classify hundreds of messages anymore. Simply upload your transcript 
              and get classified sentiments in a matter of seconds.
            </h2>
            <Link className='startButtonLink' to='/analytics'>
              <div className='header hoverable startButton'>
                Get Started
              </div>
            </Link>
          </div>
        </div>
      </div>
    </div>
  )
}
