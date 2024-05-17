# Hospital Mortality Analysis
# Overview
This was the Final Project for the Data Science tools and software course at the Faculty of Computers and Data Science, Alexandria University. (Level 3 Students)

This project focuses on analyzing hospital mortality using various data science techniques. We aim to identify key factors influencing mortality rates and apply different classification and clustering techniques to improve prediction accuracy.

# Context
The predictors of in-hospital mortality for intensive care units (ICU)-admitted heart failure (HF) patients remain poorly characterized. We aimed to develop and validate a prediction model for all-cause in-hospital mortality among ICU-admitted HF patients.

Target Variable - Outcome
* 0 - Alive
* 1 - Death

# Team Members
* Ola Mamdouh
* Maria Anwar
* Marly Magdy
* Mirna Tarek
* Verina Michel
* Mariem Nasr

# Tasks and Methodologies
## 1. Feature Selection
### Filter Techniques:

Applied two filter techniques for initial feature selection.
### Wrapper Techniques:

Used Recursive Feature Elimination (RFE) with logistic regression for feature selection.

## 2. Dimension Reduction
Applied two-dimension reduction techniques to simplify the dataset:
* Principal Component Analysis (PCA)
* Linear Discriminant Analysis (LDA)

## 3. Classification Techniques
Experimented with four different classification techniques:

* Support Vector Machine (SVM)
* XGBoost
* Decision Tree
* Stacking method (combining multiple models)
Fine-tuned each algorithm to achieve the best performance.

## 4. Handling Imbalanced Data
Used SMOTE (Synthetic Minority Over-sampling Technique) to address class imbalance in the dataset.

## 5. Clustering
* Applied PCA for dimensionality reduction before clustering.
* Used K-Nearest Neighbors (KNN) and Birch for clustering the reduced data.
* Visualized clustering results for better insights.

## 6. Visualization
* Visualized results using various plots and charts to interpret the findings.
* Utilized Power BI for advanced visualizations and interactive dashboards.
