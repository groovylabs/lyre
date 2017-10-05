import React, {Component} from 'react';
import './App.css';

class App extends Component {
  render() {
    return (
      <div className="App">
        <header className="App-header">
          <h1 className="Welcome-title">Welcome</h1>
          <h3 className="Lyre-title">Lyre REST API Mock tool</h3>
        </header>
        <p className="App-intro">
        </p>

        <div>
          <p>
            <div class="fa fa-github"></div>
            <a href="https://github.com/groovylabs/lyre">
              Check our page on github!
            </a>
          </p>
        </div>

        <div>
          <p>
            <i class="fa fa-book"></i>
            <a href="https://github.com/groovylabs/lyre/blob/master/LICENSE">
              See our documentation and start to use!
            </a>
          </p>
        </div>

        <div>
          <p>
            <i class="fa fa-money"></i>
            <a href="https://upload.wikimedia.org/wikipedia/commons/thumb/e/e0/SNice.svg/1200px-SNice.svg.png">
              Donate and help us to keep alive this project!
            </a>
          </p>
        </div>
      </div>
    );
  }
}

export default App;
