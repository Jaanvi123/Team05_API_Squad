pipeline {
    agent any
    
    tools {
        maven 'Maven-3.8.6'
        jdk 'JDK-17'
    }
    
    // Define team emails as environment variables
    environment {
        TEAM_EMAILS = 'vijayashree80sv@gmail.com,s.yogini@gmail.com,pramodini.patil182@gmail.com,Jmattalwar@gmail.com,prasannarahavi14@gmail.com,rajaya423@gmail.com'
        PROJECT_NAME = 'Team05_APISquad - LMS API Testing'
    }
    
    stages {
		
		stage('Clean Workspace') {
            steps {
                cleanWs() 
            }
        }
        
        stage('Checkout') {
            steps {
                git branch: 'main', 
                    url: 'https://github.com/s-yogini/Team05_APISquad.git'
            }
        }
        
        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }
        
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    // 1. JUnit reports (for Jenkins trends)
                    junit 'target/surefire-reports/**/*.xml'
                    
                    // 2. Allure reports
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'target/allure-results']]
                    ])
                    
                    // 3. Archive Cucumber HTML reports
                    archiveArtifacts artifacts: 'target/cucumber-reports/**/*.html', allowEmptyArchive: true
                    
                    // 4. Archive ExtentReports
                    archiveArtifacts artifacts: 'target/extent-reports/**/*.html', allowEmptyArchive: true
                }
            }
        }
        
        stage('Generate Reports') {
            steps {
                // Optional: Generate enhanced reports
                sh '''
                echo "=== TEST EXECUTION SUMMARY ==="
                echo "Project: Team05_APISquad - LMS API Testing"
                echo "=== REPORT LINKS ==="
                echo "Cucumber HTML: target/cucumber-reports/report.html"
                echo "ExtentReport: target/extent-reports/ExtentReport.html"
                echo "Allure: Use Jenkins Allure Plugin"
                '''
                
                // Publish HTML reports (if Jenkins has HTML Publisher plugin)
                publishHTML([
                    reportDir: 'target/cucumber-reports',
                    reportFiles: 'report.html',
                    reportName: 'Cucumber HTML Report',
                    keepAll: true
                ])
                
                publishHTML([
                    reportDir: 'target/extent-reports', 
                    reportFiles: 'ExtentReport.html',
                    reportName: 'ExtentReport',
                    keepAll: true
                ])
            }
        }
    }
    
    post {        
        
        success {
            echo "✅ BUILD SUCCESSFUL - Team05_APISquad"
            echo "All reports generated successfully!"
            echo "Check Jenkins for:"
            echo "1. Cucumber HTML Report"
            echo "2. ExtentReport"
            echo "3. Allure Report"
            echo "4. JUnit Trend Graphs"
            
            // Email notification for success
            emailext(
                 subject: "✅ SUCCESS: Team05_APISquad Build #${env.BUILD_NUMBER}",
                body: """<h2>✅ API Test Build Successful</h2><p><strong>Status:</strong> SUCCESS ✓</p><h3>📊 Report Links:</h3><ul><li><a href="cucumber-html-reports/">Cucumber HTML Report</a></li><li><a href="ExtentReport/">ExtentReport</a></li><li><a href="allure/">Allure Report</a></li><li><a href="testReport/">JUnit Test Results</a></li></ul><h3>👥 Team Members Notified:</h3><p>${env.TEAM_EMAILS}</p><hr><p><small>This is an automated message from Jenkins CI/CD Pipeline</small></p>""",
                to: "${env.TEAM_EMAILS}",
                attachLog: false,
                compressLog: false
            )
        }
        
        failure {
            echo "❌ BUILD FAILED - Team05_APISquad"
    		echo "Check test reports for details"
    
            
            // Email notification for failure
            emailext(
                subject: "❌ FAILURE: Team05_APISquad Build",
                              body: """<h2>❌ API Test Build FAILED</h2><p><strong>Project:</strong> ${env.PROJECT_NAME}</p><p><strong>Status:</strong> FAILED ✗</p><p><small>This is an automated message from Jenkins CI/CD Pipeline</small></p>""",
                 to: "${env.TEAM_EMAILS}",
                attachLog: true,
                compressLog: true
            )
        }
    }
}