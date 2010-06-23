require 'hk/ignition/avideo/airvideo'

class AirVideoWrapper
	include Java::hk.ignition.avideo.AirVideoClient
	attr_accessor :max_height, :max_width
	
	def initialize()
	end
	
	def connect(server, port, password)
		@client = AirVideo::Client.new(server, port, password) 		
	end
	
	def cd(path)
		@client.cd(path)
	end
	
	def ls(path)
		@client.ls(path)
	end
	
	def pwd
		@client.pwd
	end
	
	def max_height
		@client.max_height
	end
	
	def max_height=(val)
		@client.max_height = val
	end
	
	def max_width
		@client.max_width
	end
	
	def max_width=(val)
		@client.max_width = val
	end
	
end
