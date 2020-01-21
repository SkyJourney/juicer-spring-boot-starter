package com.juicer.boot;

import com.juicer.JuicerActuator;
import com.juicer.JuicerHandlerFactory;
import com.juicer.core.JuicerData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * @author SkyJourney
 */
@Controller
@RequestMapping("/juicer")
public class JuicerController {

    private final JuicerActuator juicerActuator;

    private final JuicerHandlerFactory juicerHandlerFactory;

    public JuicerController(JuicerActuator juicerActuator, JuicerHandlerFactory juicerHandlerFactory) {
        this.juicerActuator = juicerActuator;
        this.juicerHandlerFactory = juicerHandlerFactory;
    }

    @ResponseBody
    @RequestMapping(value = "/{handler}", method = {RequestMethod.GET,RequestMethod.POST})
    public List<JuicerData> getData( @PathVariable String handler) throws ExecutionException, InterruptedException {
        return juicerActuator.getDataFromHandler(handler);
    }

    @ResponseBody
    @RequestMapping(value = "/handlers", method = RequestMethod.POST)
    public Set<String> getHandlers() {
        return juicerHandlerFactory.getHandlerSet();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getHandlersView(final ModelMap model) {
        List<JuicerHandlerInfo> juicerHandlerInfos = new ArrayList<>();
        for(String handler: juicerHandlerFactory.getHandlerSet()) {
            String previous = juicerHandlerFactory.getJuicerHandler(handler).getParent();
            juicerHandlerInfos.add(new JuicerHandlerInfo(handler, "".equals(previous)?"-":previous));
        }
        model.addAttribute("handlers", juicerHandlerInfos);
        return "juicer/handler";
    }

}
