import groovy.json.JsonSlurper

def pipeline_config_json = readFileFromWorkspace("custom-pipeline-config.json")
def slurper = new groovy.json.JsonSlurper()
def pipeline_config = slurper.parseText(pipeline_config_json)


pipeline_config.each {
    target_key, target_value ->
      println $target_key
      println $target_value
}
