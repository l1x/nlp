#!/usr/bin/env bash


#unix install of virtualenv
virtualenv --version | egrep -q '[0-9]+\.[0-9]+\.[0-9]+' && echo 'virtualenv is installed...' || sudo pip install virtualenv virtualenvwrapper

#
if [ -e nlp-hackaton/.Python ]; then 
  echo "nlp-hackaton virtualenv is already created"
else
  virtualenv nlp-hackaton
fi
source nlp-hackaton/bin/activate
pip install nltk scipy ipython numpy jupyter matplotlib pandas seaborn scikit-learn sympy plotly wordcloud --upgrade
python -m nltk.downloader stopwords
#pip install --upgrade https://storage.googleapis.com/tensorflow/mac/tensorflow-0.6.0-py2-none-any.whl
jupyter notebook
