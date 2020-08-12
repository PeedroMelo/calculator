pipeline {
	agent any

	triggers {
		pollSCM('* * * * *')
	}

	stages {
		stage("Compile") {
			steps {
				sh "./gradlew compileJava"
			}
		}

		stage("Unit test") {
			steps {
				sh "./gradlew test"
			}
		}

		stage("Code Coverage") {
			steps {
				sh "./gradlew test jacocoTestReport"
				publishHTML (target: [
					reportDir: 'build/reports/jacoco/test/html',
					reportFiles: 'index.html',
					reportName: "JaCoCo Report"
				])
				sh "./gradlew test jacocoTestCoverageVerification"
			}
		}

		stage("Package") {
			steps {
				sh './gradlew build'
			}
		}

		stage("Docker Build") {
			steps {
				sh "docker build -t vieirapcmdocker/calculator ."
			}
		}

		stage("Docker Login") {
			steps {
				withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'docker-hub-credentials',
					usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
					sh "docker login --username $USERNAME --password $PASSWORD"
				}
			}
		}

		stage("Docker Push") {
			steps {
				sh "docker push vieirapcmdocker/calculator"
			}
		}

		stage("Deploy to stagging") {
			steps {
				sh "docker run -d --rm -p 8765:3333 --name calculator vieirapcmdocker/calculator"
			}
		}

		stage("Acceptance test") {
			steps {
				// sleep 30
				// sh "./gradlew acceptanceTest -Dcalculator.url=http://localhost:8765"
				echo "OK"
			}
		}
	}

	post {
     always {
        sh "docker stop calculator"
     }
	}
}
